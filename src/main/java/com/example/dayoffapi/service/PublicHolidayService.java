package com.example.dayoffapi.service;

import com.example.dayoffapi.dto.request.PublicHolidayRequest;
import com.example.dayoffapi.dto.response.PublicHolidayResponse;
import com.example.dayoffapi.exception.PublicHolidayNotFoundException;

import java.util.List;

public interface PublicHolidayService {

    PublicHolidayResponse addPublicHoliday(PublicHolidayRequest request);

    PublicHolidayResponse deletePublicHolidayById(Long id) throws PublicHolidayNotFoundException;

    PublicHolidayResponse updatePublicHoliday(Long id, PublicHolidayRequest request) throws PublicHolidayNotFoundException;

    List<PublicHolidayResponse> getAllPublicHolidays();
}
