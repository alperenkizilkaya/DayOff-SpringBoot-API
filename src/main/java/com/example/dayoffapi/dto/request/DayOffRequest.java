package com.example.dayoffapi.dto.request;

import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.entity.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class DayOffRequest {

    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    //private int numberOfDayOff;
    private String reason;

    public DayOffRequest() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayOffRequest that = (DayOffRequest) o;
        return employeeId.equals(that.employeeId) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && reason.equals(that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, startDate, endDate, reason);
    }

    @Override
    public String toString() {
        return "DayOff{" +
                "employeeId=" + employeeId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reason='" + reason + '\'' +
                '}';
    }
}
