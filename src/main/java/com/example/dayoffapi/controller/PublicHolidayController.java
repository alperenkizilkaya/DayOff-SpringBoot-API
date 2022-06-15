package com.example.dayoffapi.controller;

import com.example.dayoffapi.dto.request.PublicHolidayRequest;
import com.example.dayoffapi.dto.response.PublicHolidayResponse;
import com.example.dayoffapi.exception.PublicHolidayNotFoundException;
import com.example.dayoffapi.service.PublicHolidayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicHolidays")
@CrossOrigin
public class PublicHolidayController {

    private PublicHolidayService publicHolidayService;

    public PublicHolidayController(PublicHolidayService publicHolidayService) {
        this.publicHolidayService = publicHolidayService;
    }

    @PostMapping
    public PublicHolidayResponse addPublicHoliday(@RequestBody PublicHolidayRequest request){
        return publicHolidayService.addPublicHoliday(request);
    }

    @DeleteMapping("/{id}")
    public PublicHolidayResponse deletePublicHolidayById(@PathVariable Long id) throws PublicHolidayNotFoundException {
        return publicHolidayService.deletePublicHolidayById(id);
    }

    @PutMapping("/updatePublicHoliday/{id}")
    public PublicHolidayResponse updatePublicHoliday(@PathVariable Long id,@RequestBody PublicHolidayRequest request) throws PublicHolidayNotFoundException {
        return publicHolidayService.updatePublicHoliday(id,request);
    }

    @GetMapping
    public List<PublicHolidayResponse> getAllPublicHolidays(){
        return publicHolidayService.getAllPublicHolidays();
    }
}
