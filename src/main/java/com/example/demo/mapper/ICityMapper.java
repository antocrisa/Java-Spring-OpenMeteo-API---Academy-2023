package com.example.demo.mapper;

import com.example.demo.dto.city.CityCreateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.demo.dto.city.CityResponseDto;
import com.example.demo.dto.city.UpdateCityRequestDto;
import com.example.demo.entity.CityEntity;
import com.example.demo.model.CityModel;


@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface ICityMapper {

    CityEntity entityFromModel(CityModel cityModel);
    
    CityModel modelFromEntity(CityEntity cityEntity);

    CityResponseDto responseFromModel(CityModel cityModel);

    CityModel modelFromUpdateRequest(UpdateCityRequestDto updateCityRequestDto);

    CityModel modelFromCreateRequest(CityCreateRequestDto cityCreateRequestDto);
}
