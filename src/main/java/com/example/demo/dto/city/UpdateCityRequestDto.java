package com.example.demo.dto.city;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class UpdateCityRequestDto {

    @NotNull(message = "Should not be null")
    private Integer population;
}
