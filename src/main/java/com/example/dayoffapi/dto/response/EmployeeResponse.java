package com.example.dayoffapi.dto.response;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeeResponse {

    private String firstName;
    private String lastName;
    private LocalDate hiredDate;
    private int dayOffRights;

    public EmployeeResponse() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public int getDayOffRights() {
        return dayOffRights;
    }

    public void setDayOffRights(int dayOffRights) {
        this.dayOffRights = dayOffRights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeResponse that = (EmployeeResponse) o;
        return dayOffRights == that.dayOffRights && firstName.equals(that.firstName) && lastName.equals(that.lastName) && hiredDate.equals(that.hiredDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, hiredDate, dayOffRights);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hiredDate=" + hiredDate +
                ", dayOffRights=" + dayOffRights +
                '}';
    }
}
