package com.example.demo.controller;

import com.example.demo.constant.Path;
import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.geo.GeoResponseDto;
import com.example.demo.mapper.IGeoMapper;
import com.example.demo.service.GeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Path.GEO)
@AllArgsConstructor
public class GeoController {

    private final GeoService geoService;
    private final IGeoMapper iGeoMapper;

    @Operation(description = "Read city's coordinates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GeoResponseDto> readCoordinate(@PathVariable("id") Long id) {
        GeoResponseDto geoResponseDto = iGeoMapper.responseFromModel(geoService.readCoordinate(id));
        return ResponseEntity.ok(geoResponseDto);
    }




}
