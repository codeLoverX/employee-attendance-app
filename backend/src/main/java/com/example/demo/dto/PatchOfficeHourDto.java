package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PatchOfficeHourDto {
    String op;
    String key;
    String value;
}