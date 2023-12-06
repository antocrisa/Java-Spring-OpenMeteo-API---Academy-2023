package com.example.demo.dto.city;

import com.example.demo.dto.geo.GeoResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResponseDto {

    private Long id;

    private String name;

    private String region;

    private String province;

    private Integer population;

    private GeoResponseDto geo;



}
