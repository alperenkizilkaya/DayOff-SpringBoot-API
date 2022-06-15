package com.example.dayoffapi.config;

import com.example.dayoffapi.dto.request.DayOffRequest;
import com.example.dayoffapi.dto.request.EmployeeRequest;
import com.example.dayoffapi.dto.request.PublicHolidayRequest;
import com.example.dayoffapi.dto.response.PublicHolidayResponse;
import com.example.dayoffapi.entity.DayOff;
import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.entity.Employee;
import com.example.dayoffapi.entity.PublicHoliday;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class ConverterConfig {

    /*private static final Converter<PublicHolidayRequest, PublicHoliday> PUBLICHOLIDAY_REQUEST_TO_PUBLICHOLIDAY_CONVERTER =
            context -> {
                PublicHoliday entity = new PublicHoliday();
                entity.setDate(context.getSource().getDate());
                entity.setName(context.getSource().getName());
                return entity;
            };

    private static final Converter<PublicHoliday, PublicHolidayResponse> PUBLICHOLIDAY_TO_PUBLICHOLIDAY_RESPONSE_CONVERTER =
            context -> {
                PublicHolidayResponse response = new PublicHolidayResponse();
                response.setDate(context.getSource().getDate());
                response.setName(context.getSource().getName());
                return response;
            };*/

    private static final Converter<DayOffRequest, DayOff> DAY_OFF_REQUEST_TO_DAY_OFF_CONVERTER =
            context -> {
                DayOff dayOff = new DayOff();;
                dayOff.setStartDate(context.getSource().getStartDate());
                dayOff.setEndDate(context.getSource().getEndDate());
                dayOff.setReason(context.getSource().getReason());
                dayOff.setStatus(DayOffStatus.WAITING);
                return dayOff;
            };

/*    private static final Converter<Employee, EmployeeRequest> EMPLOYEE_TO_EMPLOYEE_REQUEST_CONVERTER =
            context -> {
                EmployeeRequest request = new EmployeeRequest();;
                request.setFirstName(context.getSource().getFirstName());
                request.setLastName(context.getSource().getLastName());
                request.setHiredDate(context.getSource().getHiredDate());
                request.setDayOffRights(context.getSource().getDayOffRights());
                return request;
            };*/

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
       /* mapper.addConverter(PUBLICHOLIDAY_REQUEST_TO_PUBLICHOLIDAY_CONVERTER, PublicHolidayRequest.class, PublicHoliday.class);
        mapper.addConverter(PUBLICHOLIDAY_TO_PUBLICHOLIDAY_RESPONSE_CONVERTER, PublicHoliday.class, PublicHolidayResponse.class);
        */
        mapper.addConverter(DAY_OFF_REQUEST_TO_DAY_OFF_CONVERTER, DayOffRequest.class, DayOff.class);
        //mapper.addConverter(EMPLOYEE_TO_EMPLOYEE_REQUEST_CONVERTER, Employee.class, EmployeeRequest.class);
        return mapper;
    }
}
