package com.example.dayoffapi.controller;

import com.example.dayoffapi.dto.request.DayOffRequest;
import com.example.dayoffapi.dto.response.DayOffResponse;
import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.exception.*;
import com.example.dayoffapi.service.DayOffService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/dayOffs")
@CrossOrigin
public class DayOffController {

    private DayOffService dayOffService;

    public DayOffController(DayOffService dayOffService) {
        this.dayOffService = dayOffService;
    }

    @PostMapping
    public DayOffResponse sendDayOffRequest(@RequestBody DayOffRequest request) throws DayOffRightsNotEnoughtException, DateProblemException, ParseException, EmployeeNotFoundException, EmployeeAlreadyHaveDayOffRequest {
        return dayOffService.sendDayOffRequest(request);
    }

    @GetMapping("/getDayOffRequestById")
    public DayOffResponse getDayOffRequestById(Long id) throws DayOffNotFoundException {
        return dayOffService.getDayOffById(id);
    }

    @GetMapping("/getAllDayOffs")
    public List<DayOffResponse> getAllDayOffRequests(){
        return dayOffService.getAllDayOffs();
    }

    @PutMapping
    public DayOffResponse updateDayOffRequestById(Long id, DayOffStatus status) throws DayOffNotFoundException {
        return dayOffService.updateDayOffRequestById(id, status);
    }
}
