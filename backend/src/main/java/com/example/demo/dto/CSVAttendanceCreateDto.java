package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CSVAttendanceCreateDto {

    String employeeName;
    String designation;
    String department;
    String officeIN;
    String officeOut;
    String date;

    @Override
    public String toString() {
        return "Employee [name=" + employeeName + ", designation=" + designation + ", department=" + department
                + ", OfficeIN=" + officeIN + ", OfficeOut=" + officeOut + ", Date=" + date +
                "]";
    }
}
