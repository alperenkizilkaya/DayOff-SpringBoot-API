package com.example.dayoffapi.dto.response;

import java.time.LocalDate;
import java.util.Objects;

public class PublicHolidayResponse {

    private LocalDate date;
    private String name;

    public PublicHolidayResponse() {
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
        PublicHolidayResponse that = (PublicHolidayResponse) o;
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
