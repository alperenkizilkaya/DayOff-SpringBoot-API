package com.example.dayoffapi.repository;

import com.example.dayoffapi.entity.DayOff;
import com.example.dayoffapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, Long> {
    List<DayOff> findByEmployeeId(Employee employeeId);
}
