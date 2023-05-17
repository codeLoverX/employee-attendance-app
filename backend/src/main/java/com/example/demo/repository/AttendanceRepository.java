package com.example.demo.repository;

import com.example.demo.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    public List<Attendance> findByEmployeeId(Long employeeId);

    public List<Attendance> findByEmployeeIdIn(List<Long> employeeId);

    public List<Attendance> findByEmployeeIdInOrderByEmployeeId(List<Long> employeeId);
}
