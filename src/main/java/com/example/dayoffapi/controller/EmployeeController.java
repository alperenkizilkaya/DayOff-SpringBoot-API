package com.example.dayoffapi.controller;

import com.example.dayoffapi.dto.request.EmployeeRequest;
import com.example.dayoffapi.dto.response.EmployeeResponse;
import com.example.dayoffapi.exception.EmployeeNotFoundException;
import com.example.dayoffapi.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest request){
        return employeeService.addEmployee(request);
    }

    @DeleteMapping("/{id}")
    public EmployeeResponse deleteEmployeeById(@PathVariable Long id) throws EmployeeNotFoundException {
        return employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/updateEmployee/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) throws EmployeeNotFoundException {
        return employeeService.updateEmployee(id, request);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/employeesByDayOffRights/{dayOffRights}")
    public List<EmployeeResponse> getEmployeesByDayOffRights(@PathVariable int dayOffRights) {
        return employeeService.getEmployeesByDayOffRights(dayOffRights);
    }

}
