package com.example.demo.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoModel {
    

    private Long id;

    private String name;

    private BigDecimal lng;

    private BigDecimal lat;
}
