package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AvgModel {


    private Double averageTemperature;

    private LocalDate startDate;

    private LocalDate endDate;
}
