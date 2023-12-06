package com.example.demo.dto.city;

import com.example.demo.dto.geo.GeoRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityRequestDto {

    @NotNull(message = "Should not be null")
    private String name;

    @NotNull(message = "Should not be null")
    @NotBlank(message = "Should not be blank")
    private String region;

    @NotNull(message = "Should not be null")
    @NotBlank(message = "Should not be blank")
    @Length(min = 2, max = 2, message = "the length must be 2 characters")
    private String province;

    @NotNull(message = "Should not be null")
    private Integer population;

    @NotNull
    private GeoRequestDto geoRequestDto;
}
