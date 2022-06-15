package com.example.dayoffapi.service.business;

import com.example.dayoffapi.dto.request.PublicHolidayRequest;
import com.example.dayoffapi.dto.response.PublicHolidayResponse;
import com.example.dayoffapi.entity.PublicHoliday;
import com.example.dayoffapi.exception.PublicHolidayNotFoundException;
import com.example.dayoffapi.repository.PublicHolidayRepository;
import com.example.dayoffapi.service.PublicHolidayService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicHolidayServiceImpl implements PublicHolidayService {

    private PublicHolidayRepository publicHolidayRepository;
    private ModelMapper modelMapper;

    public PublicHolidayServiceImpl(PublicHolidayRepository publicHolidayRepository, ModelMapper modelMapper) {
        this.publicHolidayRepository = publicHolidayRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PublicHolidayResponse addPublicHoliday(PublicHolidayRequest request) {

        PublicHoliday entity = modelMapper.map(request, PublicHoliday.class);
        PublicHoliday savedEntity = publicHolidayRepository.save(entity);
        return modelMapper.map(savedEntity, PublicHolidayResponse.class);
    }

    @Override
    public PublicHolidayResponse deletePublicHolidayById(Long id) throws PublicHolidayNotFoundException {
        PublicHoliday entity = publicHolidayRepository.findById(id)
                .orElseThrow(() -> new PublicHolidayNotFoundException());
        publicHolidayRepository.deleteById(id);
        return modelMapper.map(entity, PublicHolidayResponse.class);
    }

    @Override
    public PublicHolidayResponse updatePublicHoliday(Long id, PublicHolidayRequest request) throws PublicHolidayNotFoundException {
        PublicHoliday entity = publicHolidayRepository.findById(id)
                                                      .orElseThrow(() -> new PublicHolidayNotFoundException());
        modelMapper.map(request, entity);
        return modelMapper.map(publicHolidayRepository.save(entity),PublicHolidayResponse.class);
    }

    @Override
    public List<PublicHolidayResponse> getAllPublicHolidays() {
        List<PublicHoliday> holidays = publicHolidayRepository.findAll();
        List<PublicHolidayResponse> responses = holidays.stream()
                                                    .map( holiday -> modelMapper.map(holiday, PublicHolidayResponse.class))
                                                    .collect(Collectors.toList());
        return responses;
    }
}
