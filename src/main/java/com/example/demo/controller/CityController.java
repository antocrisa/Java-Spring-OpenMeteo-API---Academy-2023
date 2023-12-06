package com.example.demo.controller;

import com.example.demo.constant.Path;
import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.city.CityResponseDto;
import com.example.demo.dto.city.CityCreateRequestDto;
import com.example.demo.dto.city.UpdateCityRequestDto;
import com.example.demo.mapper.ICityMapper;
import com.example.demo.model.CityModel;
import com.example.demo.service.CityService;
import com.example.demo.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(Path.CITIES)
public class CityController {

    private final ICityMapper iCityMapper;
    private final CityService cityService;
    private final ValidationService validationService;

    @Operation(description = "Find a city by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<CityResponseDto>> findByName(@RequestParam(value = "name") String name) {
        
        List<CityResponseDto> cityResponseDtos = cityService.findByName(name).stream()
                .map(iCityMapper::responseFromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityResponseDtos);

   }

    @Operation(description = "Find a city by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CityResponseDto> findById(@PathVariable("id") Long id) {

        CityResponseDto cityResponseDto = iCityMapper.responseFromModel(cityService.findById(id));
        return ResponseEntity.ok(cityResponseDto);

    }

    @Operation(description = "Update a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CityResponseDto> updatePopulationById(@PathVariable("id") Long id, @RequestBody UpdateCityRequestDto updateCityRequestDto) {
        validationService.doValidate(updateCityRequestDto);
        CityModel cityModel = cityService.updatePopulationById(id, iCityMapper.modelFromUpdateRequest(updateCityRequestDto));
        return ResponseEntity.ok(iCityMapper.responseFromModel(cityModel));
    }

    @Operation(description = "Create a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PostMapping
    public ResponseEntity<CityResponseDto> createCity(@RequestBody CityCreateRequestDto cityCreateRequestDto){
        validationService.doValidate(cityCreateRequestDto);
        CityModel cityModel = cityService.createCity(iCityMapper.modelFromCreateRequest(cityCreateRequestDto));

        URI uri = UriComponentsBuilder.fromPath("CITIES")
                .pathSegment(cityCreateRequestDto.getId().toString())
                .build()
                .toUri();

        return ResponseEntity.created(uri).body(iCityMapper.responseFromModel(cityModel));
    }

    @Operation(description = "Delete a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable("id") Long id) {
        cityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Search all cities with a greater number of inhabitants than")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PostMapping("/search-by-inhabitants")
    public ResponseEntity<List<CityResponseDto>> getCitiesWithPopulationGreaterThan(@RequestParam Integer population){

        List<CityResponseDto> cityResponseDtos = cityService.getCitiesWithPopulationGreaterThan(population).stream()
                .map(iCityMapper::responseFromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityResponseDtos);
    }

    @Operation(description = "Search all cities with a greater number of inhabitants in a given Region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PostMapping("/region/{region}/search-by-inhabitants")
    public ResponseEntity<List<CityResponseDto>> getCitiesByRegionWithPopulationGreaterThan(@PathVariable ("region") String region, @RequestParam Integer population){

        List<CityResponseDto> cityResponseDtos = cityService.getCitiesByRegionWithPopulationGreaterThan(region, population).stream()
                .map(iCityMapper::responseFromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityResponseDtos);
    }


    @Operation(description = "Search all cities with a greater number of inhabitants in a given Province")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PostMapping("/province/{province}/search-by-inhabitants")
    public ResponseEntity<List<CityResponseDto>> getCitiesByProvinceWithPopulationGreaterThanAndNameNot(@PathVariable("province") String province, @RequestParam Integer population){

        List<CityResponseDto> cityResponseDtos = cityService.getCitiesByProvinceWithPopulationGreaterThan(province, population).stream()
                .map(iCityMapper::responseFromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityResponseDtos);
    }
}
