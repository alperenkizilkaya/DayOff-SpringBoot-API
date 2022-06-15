package com.example.dayoffapi.dto.response;

import com.example.dayoffapi.entity.DayOffStatus;
import com.example.dayoffapi.entity.Employee;

import java.time.LocalDate;
import java.util.Objects;

public class DayOffResponse {

    private EmployeeResponse employeeResponse;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfDayOff;
    private String reason;
    private DayOffStatus status;

    public DayOffResponse() {
    }

    public EmployeeResponse getEmployeeResponse() {
        return employeeResponse;
    }

    public void setEmployeeResponse(EmployeeResponse employeeResponse) {
        this.employeeResponse = employeeResponse;
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

    public int getNumberOfDayOff() {
        return numberOfDayOff;
    }

    public void setNumberOfDayOff(int numberOfDayOff) {
        this.numberOfDayOff = numberOfDayOff;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DayOffStatus getStatus() {
        return status;
    }

    public void setStatus(DayOffStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayOffResponse that = (DayOffResponse) o;
        return numberOfDayOff == that.numberOfDayOff && employeeResponse.equals(that.employeeResponse) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && reason.equals(that.reason) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeResponse, startDate, endDate, numberOfDayOff, reason, status);
    }

    @Override
    public String toString() {
        return "DayOffResponse{" +
                "employeeResponse=" + employeeResponse +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", numberOfDayOff=" + numberOfDayOff +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }
}
