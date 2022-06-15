package com.example.dayoffapi.service.business;

import com.example.dayoffapi.dto.request.EmployeeRequest;
import com.example.dayoffapi.dto.response.EmployeeResponse;
import com.example.dayoffapi.dto.response.PublicHolidayResponse;
import com.example.dayoffapi.entity.Employee;
import com.example.dayoffapi.entity.PublicHoliday;
import com.example.dayoffapi.exception.EmployeeNotFoundException;
import com.example.dayoffapi.exception.PublicHolidayNotFoundException;
import com.example.dayoffapi.repository.EmployeeRepository;
import com.example.dayoffapi.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest request) {
        Employee entity = modelMapper.map(request, Employee.class);
        checkDayOffRight(entity);
        Employee savedEntity = employeeRepository.save(entity);
        return modelMapper.map(savedEntity, EmployeeResponse.class);
    }

    void checkDayOffRight(Employee entity){
        LocalDate now = LocalDate.now();
        LocalDate hiredDate = LocalDate.from(entity.getHiredDate());
        long intervalDays = ChronoUnit.DAYS.between(hiredDate, now);
        System.out.println("Total number of days between dates:" + intervalDays);
        if(intervalDays < 365)
            entity.setDayOffRights(5);
        else if(intervalDays >= 365 && intervalDays < 365*5)
            entity.setDayOffRights(15);
        else if(intervalDays >= 365*5 && intervalDays < 365*10)
            entity.setDayOffRights(18);
        else if(intervalDays >= 365*10)
            entity.setDayOffRights(24);
    }

    @Override
    public EmployeeResponse deleteEmployeeById(Long id) throws EmployeeNotFoundException {
        Employee entity = employeeRepository.findById(id)
                                    .orElseThrow( ()-> new EmployeeNotFoundException());
        employeeRepository.deleteById(id);
        return modelMapper.map(entity, EmployeeResponse.class);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) throws EmployeeNotFoundException {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException());
        modelMapper.map(request, entity);
        checkDayOffRight(entity);
        return modelMapper.map(employeeRepository.save(entity), EmployeeResponse.class);
    }
    public EmployeeResponse updateEmployeeForDayOffRights(Long id, EmployeeRequest request, int dayOfRights) throws EmployeeNotFoundException {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException());
        modelMapper.map(request, entity);
        entity.setDayOffRights(dayOfRights);
        return modelMapper.map(employeeRepository.save(entity), EmployeeResponse.class);
    }

    @Override
    public EmployeeResponse updateEmployeeForRejectedDayOffRequest(Long id, EmployeeRequest request, int dayOfNumber) throws EmployeeNotFoundException {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException());
        modelMapper.map(request, entity);
        entity.setDayOffRights(entity.getDayOffRights()+dayOfNumber);
        return modelMapper.map(employeeRepository.save(entity), EmployeeResponse.class);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) throws EmployeeNotFoundException {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow( () -> new EmployeeNotFoundException());
        return modelMapper.map(entity,EmployeeResponse.class);
    }

    @Override
    public Employee findEmployeeById(Long id) throws EmployeeNotFoundException {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow( () -> new EmployeeNotFoundException());
        return entity;
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDayOffRights(int dayOffRights) {
        List<Employee> entities = employeeRepository.findByDayOffRights(dayOffRights);

        List<EmployeeResponse> responses = entities.stream()
                                                .map( entity -> modelMapper.map(entity, EmployeeResponse.class)).collect(Collectors.toList());
        return responses;
    }


}
