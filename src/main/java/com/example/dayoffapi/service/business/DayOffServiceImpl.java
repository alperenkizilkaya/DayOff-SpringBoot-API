package com.example.dayoffapi.service.business;

import com.example.dayoffapi.dto.request.DayOffRequest;
import com.example.dayoffapi.dto.request.EmployeeRequest;
import com.example.dayoffapi.dto.response.DayOffResponse;
import com.example.dayoffapi.dto.response.EmployeeResponse;
import com.example.dayoffapi.dto.response.PublicHolidayResponse;
import com.example.dayoffapi.entity.DayOff;
import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.entity.Employee;
import com.example.dayoffapi.exception.*;
import com.example.dayoffapi.repository.DayOffRepository;
import com.example.dayoffapi.service.DayOffService;
import com.example.dayoffapi.service.EmployeeService;
import com.example.dayoffapi.service.PublicHolidayService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DayOffServiceImpl implements DayOffService {

    private DayOffRepository dayOffRepository;
    private ModelMapper modelMapper;
    private EmployeeService employeeService;
    private PublicHolidayService publicHolidayService;

    public DayOffServiceImpl(DayOffRepository dayOffRepository, ModelMapper modelMapper, EmployeeService employeeService, PublicHolidayService publicHolidayService) {
        this.dayOffRepository = dayOffRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.publicHolidayService = publicHolidayService;
    }

    @Override
    public DayOffResponse sendDayOffRequest(DayOffRequest request) throws EmployeeNotFoundException, DateProblemException, ParseException, DayOffRightsNotEnoughtException, EmployeeAlreadyHaveDayOffRequest {
        Employee employee = checkIsEmployeeExist(request.getEmployeeId());
        isDatesOkay(request.getStartDate(), request.getEndDate());
        int numberOfDayOff = dateDifferences(request.getStartDate(), request.getEndDate());
        checkDayOffRightsAndDateDifferences(employee.getDayOffRights(), numberOfDayOff);

        checkEmployeeAlreadyHaveDayOffRequest(employee);
        EmployeeResponse updatedEmployee = employeeOperations(employee, numberOfDayOff);

        DayOff dayOff = modelMapper.map(request, DayOff.class);
        dayOff.setEmployeeId(employee);
        dayOff.setNumberOfDayOff(numberOfDayOff);
        DayOff savedEntity = dayOffRepository.save(dayOff);

        DayOffResponse response = modelMapper.map(savedEntity, DayOffResponse.class);
        response.setEmployeeResponse(updatedEmployee);
        return response;
    }

    Employee checkIsEmployeeExist(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeService.findEmployeeById(employeeId);
        return employee;
    }

    void isDatesOkay(LocalDate startDate, LocalDate endDate) throws DateProblemException {
        if(startDate.isAfter(endDate) || startDate.isEqual(endDate))
            throw new DateProblemException();
    }

    int dateDifferences(LocalDate startDate, LocalDate endDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = df.parse(startDate.toString());
        Date date2 = df.parse(endDate.toString());

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        List<PublicHolidayResponse> holidays = publicHolidayService.getAllPublicHolidays();
        AtomicInteger numberOfDays = new AtomicInteger();
        while (cal1.before(cal2)) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                    &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays.getAndIncrement();
            }//public holidays also must be exclude
            if(holidays.size()>0){
                for(int i=0; i< holidays.size(); i++){
                    Date date3 = df.parse(holidays.get(i).getDate().toString());
                    cal3.setTime(date3);
                    if ( cal1.equals(cal3) ) {
                        numberOfDays.getAndDecrement();
                    }
                }
            }
            cal1.add(Calendar.DATE,1);
        }
        /*holidays.stream()
                .mapToInt(holiday -> {
                    cal1.setTime(date1);
                    try {
                        date3.set(df.parse(holiday.toString()));
                        cal3.setTime(date3.get());
                        while (cal1.before(cal2)) {
                            if ( cal1.equals(cal3) ) {
                                numberOfDays.getAndDecrement();
                            }
                            cal1.add(Calendar.DATE,1);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return numberOfDays.get();
                });*/
        return numberOfDays.get();
    }

    void checkDayOffRightsAndDateDifferences(int rights, int holidayDayNumber) throws DayOffRightsNotEnoughtException {
        if(holidayDayNumber > rights)
            throw new DayOffRightsNotEnoughtException();
    }

    EmployeeResponse employeeOperations(Employee employee, int numberOfDayOff) throws EmployeeNotFoundException {
        int updatedRights = employee.getDayOffRights() - numberOfDayOff;
        employee.setDayOffRights(updatedRights);
        EmployeeRequest req = modelMapper.map(employee, EmployeeRequest.class);
        EmployeeResponse updatedEmployee = employeeService.updateEmployeeForDayOffRights(employee.getId(), req, updatedRights);
        updatedEmployee.setDayOffRights(updatedRights);
        return updatedEmployee;
    }

    void checkEmployeeAlreadyHaveDayOffRequest(Employee emp) throws EmployeeAlreadyHaveDayOffRequest {
        List<DayOff> empDayOffs = dayOffRepository.findByEmployeeId(emp);
        if(!empDayOffs.isEmpty()) {
            for(int i=0; i<empDayOffs.size();i++){
                if (empDayOffs.get(i).getStatus().equals(DayOffStatus.WAITING)) {
                    throw new EmployeeAlreadyHaveDayOffRequest();
                }
            }
        }
    }

    @Override
    public DayOffResponse getDayOffById(Long id) throws DayOffNotFoundException {
        DayOff entity = dayOffRepository.findById(id)
                                        .orElseThrow( () -> new DayOffNotFoundException() );
        DayOffResponse response = modelMapper.map(entity, DayOffResponse.class);
        Employee emp = entity.getEmployeeId();
        response.setEmployeeResponse(modelMapper.map(emp, EmployeeResponse.class));
        return response;
    }

    @Override
    public List<DayOffResponse> getAllDayOffs() {
        List<DayOff> dayOffs= dayOffRepository.findAll();
        List<DayOffResponse> responses = dayOffs.stream()
                                                .map( dayOff -> {
                                                        DayOffResponse response = modelMapper.map(dayOff, DayOffResponse.class);
                                                        response.setEmployeeResponse(modelMapper.map(dayOff.getEmployeeId(),EmployeeResponse.class));
                                                        return response;
                                                }).collect(Collectors.toList());
        return responses;
    }

    @Override
    public DayOff findDayOffById(Long id) throws DayOffNotFoundException {
        DayOff entity = dayOffRepository.findById(id)
                .orElseThrow( () -> new DayOffNotFoundException() );
        return entity;
    }

    @Override
    public DayOffResponse updateDayOffRequestById(Long id, DayOffStatus status) throws DayOffNotFoundException {
        DayOff entity = dayOffRepository.findById(id)
                .orElseThrow( () -> new DayOffNotFoundException() );
        entity.setStatus(status);
        DayOff updatedDayOff = dayOffRepository.save(entity);
        DayOffResponse response = modelMapper.map(updatedDayOff, DayOffResponse.class);
        response.setEmployeeResponse(modelMapper.map(entity.getEmployeeId(), EmployeeResponse.class));
        return response;
    }
}
