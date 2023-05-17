package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OutputOfficeHourDto {
    private Long id;
    private String officeStart;
    private String officeEnd;
}

