package com.example.demo.controller;

import com.example.demo.constant.Path;
import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.weather.AvgResponseDto;
import com.example.demo.dto.weather.WeatherResponseDto;
import com.example.demo.mapper.IWeatherMapper;
import com.example.demo.service.ValidationService;
import com.example.demo.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping(Path.WEATHER)
public class WeatherController {


    private final WeatherService weatherService;

    private final IWeatherMapper iWeatherMapper;
    private final ValidationService validationService;

    @Operation(description = "Weather forecast for 7 days")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/search/city/{id}")
    public ResponseEntity<WeatherResponseDto> getWeather(@PathVariable("id")Long id) {
        WeatherResponseDto weatherResponseDto = iWeatherMapper.responseFromModel(weatherService.readWeather(id));
        return ResponseEntity.ok(weatherResponseDto);

    }


    @Operation(description = "Average temperature of a City for a range of days in the past until today")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/search/city/{id}/average-temperature")
    public ResponseEntity<AvgResponseDto> getAvgTemperatureByCityAndNumberOfDays(@PathVariable("id") Long id, @RequestParam Integer days){
        validationService.doValidateDays(days);
        AvgResponseDto avgResponseDto = iWeatherMapper.avgResponseFromModel(weatherService.getAvgTemperatureByCityAndNumberOfDays(id, days));
        return ResponseEntity.ok(avgResponseDto);
    }



    @Operation(description = "Average temperature of all province in a given date (in the past)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/search/province/{province}/average-temperature")
    public ResponseEntity<AvgResponseDto> getAvgTemperatureByProvinceAndDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @PathVariable("province") String province){
        validationService.doValidateDate(date);
        AvgResponseDto avgResponseDto = iWeatherMapper.avgResponseFromModel(weatherService.getAvgTemperatureByProvince(date, province));
        return ResponseEntity.ok(avgResponseDto);

    }

}
