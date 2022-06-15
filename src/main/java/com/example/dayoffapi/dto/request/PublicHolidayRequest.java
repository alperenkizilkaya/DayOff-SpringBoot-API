package com.example.dayoffapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class PublicHolidayRequest {

    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate date;
    private String name;

    public PublicHolidayRequest() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicHolidayRequest that = (PublicHolidayRequest) o;
        return date.equals(that.date) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, name);
    }

    @Override
    public String toString() {
        return "PublicHoliday{" +
                "date=" + date +
                ", name='" + name + '\'' +
                '}';
    }
}
