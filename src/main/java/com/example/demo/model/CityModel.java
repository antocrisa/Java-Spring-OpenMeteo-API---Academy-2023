package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityModel {
  
    private Long id;

    private String name;

    private String region;

    private String province;

    private Integer population;

    private GeoModel geo;
}
