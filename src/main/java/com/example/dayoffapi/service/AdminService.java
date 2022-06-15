package com.example.dayoffapi.service;

import com.example.dayoffapi.dto.response.DayOffResponse;
import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.exception.AlreadyAnsweredDayOffRequestException;
import com.example.dayoffapi.exception.DayOffNotFoundException;
import com.example.dayoffapi.exception.EmployeeNotFoundException;

public interface AdminService {

    DayOffResponse decideDayOffRequest(Long id, DayOffStatus status) throws DayOffNotFoundException, EmployeeNotFoundException, AlreadyAnsweredDayOffRequestException;
}
