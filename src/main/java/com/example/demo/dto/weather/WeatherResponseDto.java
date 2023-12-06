package com.example.demo.dto.weather;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Getter
@Setter
public class WeatherResponseDto {


    @NotBlank
    private BigDecimal latitude;
    @NotBlank
    private BigDecimal longitude;
    @NotNull
    private HourlyResponseDto hourly;
}
