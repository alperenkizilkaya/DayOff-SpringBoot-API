package com.example.dayoffapi.service;

import com.example.dayoffapi.dto.request.DayOffRequest;
import com.example.dayoffapi.dto.response.DayOffResponse;
import com.example.dayoffapi.entity.DayOff;
import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.exception.*;

import java.text.ParseException;
import java.util.List;

public interface DayOffService {
    DayOffResponse sendDayOffRequest(DayOffRequest request) throws EmployeeNotFoundException, DateProblemException, ParseException, DayOffRightsNotEnoughtException, EmployeeAlreadyHaveDayOffRequest;

    DayOffResponse getDayOffById(Long id) throws DayOffNotFoundException;

    List<DayOffResponse> getAllDayOffs();

    DayOff findDayOffById(Long id) throws DayOffNotFoundException;

    DayOffResponse updateDayOffRequestById(Long id, DayOffStatus status) throws DayOffNotFoundException;
}
