package com.example.dayoffapi.service;

import com.example.dayoffapi.dto.request.EmployeeRequest;
import com.example.dayoffapi.dto.response.EmployeeResponse;
import com.example.dayoffapi.entity.Employee;
import com.example.dayoffapi.exception.EmployeeNotFoundException;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse addEmployee(EmployeeRequest request);

    EmployeeResponse deleteEmployeeById(Long id) throws EmployeeNotFoundException;

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request) throws EmployeeNotFoundException;

    EmployeeResponse updateEmployeeForDayOffRights(Long id, EmployeeRequest request, int dayOfRights) throws EmployeeNotFoundException;

    EmployeeResponse getEmployeeById(Long id) throws EmployeeNotFoundException;

    List<EmployeeResponse> getEmployeesByDayOffRights(int dayOffRights);

    Employee findEmployeeById(Long employeeId) throws EmployeeNotFoundException;

    EmployeeResponse updateEmployeeForRejectedDayOffRequest(Long id, EmployeeRequest request, int dayOfNumber) throws EmployeeNotFoundException;
}
