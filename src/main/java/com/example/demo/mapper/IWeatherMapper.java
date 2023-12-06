package com.example.demo.mapper;


import com.example.demo.dto.weather.AvgResponseDto;
import com.example.demo.dto.weather.WeatherResponseDto;
import com.example.demo.model.AvgModel;
import com.example.demo.model.WeatherModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IWeatherMapper {

    WeatherResponseDto responseFromModel(WeatherModel weatherModel);

    AvgResponseDto avgResponseFromModel(AvgModel avgModel);

    WeatherModel modelFromResponse(WeatherResponseDto weatherResponseDto);
}
