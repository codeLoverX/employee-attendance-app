package com.example.demo.dto;

import com.example.demo.entity.Attendance;
import com.example.demo.entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public class OutputAttendanceDto {
    private Long id;
    private String officeIn;
    private String officeOut;
    private boolean lateOfficeIn;
    private boolean earlyOfficeOut;
    private String date;
    @JsonProperty("employee")
    private Employee employee;
}
