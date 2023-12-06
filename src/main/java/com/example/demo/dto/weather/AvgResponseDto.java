package com.example.demo.dto.weather;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AvgResponseDto {

    @Schema(description = "Average temperature")
    private Double averageTemperature;

    @Schema(description = "Start day of range date")
    private LocalDate startDate;

    @Schema(description = "End day of range date")
    private LocalDate endDate;
}