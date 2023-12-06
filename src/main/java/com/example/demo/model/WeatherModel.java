package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WeatherModel {



    private BigDecimal latitude;

    private BigDecimal longitude;

    private HourlyModel hourly;








}
