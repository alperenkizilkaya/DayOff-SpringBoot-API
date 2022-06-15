package com.example.dayoffapi.controller;

import com.example.dayoffapi.dto.response.DayOffResponse;
import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.exception.AlreadyAnsweredDayOffRequestException;
import com.example.dayoffapi.exception.DayOffNotFoundException;
import com.example.dayoffapi.exception.EmployeeNotFoundException;
import com.example.dayoffapi.service.AdminService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/dayOffRequest")
    DayOffResponse acceptOrRejectDayOffRequest(Long id, DayOffStatus status) throws DayOffNotFoundException, EmployeeNotFoundException, AlreadyAnsweredDayOffRequestException {
        return adminService.decideDayOffRequest(id, status);
    }

}
