package com.example.dayoffapi.service.business;

import com.example.dayoffapi.dto.request.EmployeeRequest;
import com.example.dayoffapi.dto.response.DayOffResponse;
import com.example.dayoffapi.dto.response.EmployeeResponse;
import com.example.dayoffapi.entity.DayOff;
import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.entity.Employee;
import com.example.dayoffapi.exception.AlreadyAnsweredDayOffRequestException;
import com.example.dayoffapi.exception.DayOffNotFoundException;
import com.example.dayoffapi.exception.EmployeeNotFoundException;
import com.example.dayoffapi.service.AdminService;
import com.example.dayoffapi.service.DayOffService;
import com.example.dayoffapi.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private DayOffService dayOffService;
    private EmployeeService employeeService;
    private ModelMapper modelMapper;

    public AdminServiceImpl(DayOffService dayOffService, EmployeeService employeeService, ModelMapper modelMapper) {
        this.dayOffService = dayOffService;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public DayOffResponse decideDayOffRequest(Long id, DayOffStatus status) throws DayOffNotFoundException, EmployeeNotFoundException, AlreadyAnsweredDayOffRequestException {
        DayOff entity = dayOffService.findDayOffById(id);
        dayOffStatusControl(entity);
        entity.setStatus(status);
        DayOffResponse dayOfResponse = modelMapper.map(entity, DayOffResponse.class);
        if(status.equals(DayOffStatus.REJECTED)){
            Employee emp = entity.getEmployeeId();
            int numberOfDayOff = entity.getNumberOfDayOff();
            EmployeeResponse empResponse = employeeService.updateEmployeeForRejectedDayOffRequest(emp.getId(), modelMapper.map(emp, EmployeeRequest.class), numberOfDayOff);
            dayOfResponse.setEmployeeResponse(empResponse);
        }else{
            dayOfResponse.setEmployeeResponse(modelMapper.map(entity.getEmployeeId(), EmployeeResponse.class));
        }
        dayOffService.updateDayOffRequestById(entity.getId(), status);
        return dayOfResponse;
    }

    void dayOffStatusControl(DayOff dayOff) throws AlreadyAnsweredDayOffRequestException {
        if(dayOff.getStatus().equals(DayOffStatus.ACCEPTED) || dayOff.getStatus().equals(DayOffStatus.REJECTED)){
            throw new AlreadyAnsweredDayOffRequestException();
        }
    }
}
