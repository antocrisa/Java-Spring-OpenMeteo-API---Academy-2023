package com.example.demo.dto.geo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
public class GeoRequestDto {

    @NotBlank(message = "should not be blank")
    private BigDecimal lng;

    @NotBlank(message = "should not be blank")
    private BigDecimal lat;

}
