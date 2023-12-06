package com.example.demo;

import com.example.demo.dto.city.CityCreateRequestDto;
import com.example.demo.dto.city.UpdateCityRequestDto;
import com.example.demo.dto.geo.GeoRequestDto;
import com.example.demo.model.CityModel;
import com.example.demo.model.GeoModel;
import com.example.demo.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CityTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @Autowired
    ObjectMapper objectMapper;
    List<CityModel> cityModels;
    CityModel cityModel = new CityModel();

    @BeforeAll
    void createMock (){

        cityModel.setName("Roma");
        cityModel.setId((long) 58091);
        cityModel.setProvince("RM");
        cityModel.setRegion("Lazio");
        cityModel.setPopulation(2638842);

        GeoModel geoModel = new GeoModel();
        geoModel.setLat(BigDecimal.valueOf(41.89277044));
        geoModel.setLng(BigDecimal.valueOf(12.48366723));
        geoModel.setId((long) 58091);
        geoModel.setName("Roma");

        cityModel.setGeo(geoModel);

        cityModels = List.of(cityModel);
    }

    @Test
    void testGetCitiesByName() throws Exception {

        when(cityService.findByName("Roma")).thenReturn(cityModels);

        mockMvc.perform(get("/v1.0/cities")
                        .param("name", "Roma")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Roma"));

        verify(cityService).findByName("Roma");

        assertEquals(cityModels.size(), 1);
        assertEquals(cityModels.get(0).getName(), "Roma");
    }

    @Test
    void testGetCitiesById() throws Exception{

        when(cityService.findById(58091L)).thenReturn(cityModel);

        mockMvc.perform(get("/v1.0/cities/58091")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Roma"));

        verify(cityService).findById(58091L);
    }


    @Test
    void testCreateACity() throws Exception {

        CityCreateRequestDto cityCreateRequestDto = new CityCreateRequestDto();

        GeoRequestDto geoRequestDto = new GeoRequestDto();
        geoRequestDto.setLng(BigDecimal.valueOf(12.48366723));
        geoRequestDto.setLat(BigDecimal.valueOf(41.89277044));

        cityCreateRequestDto.setGeoRequestDto(geoRequestDto);
        cityCreateRequestDto.setId(1L);
        cityCreateRequestDto.setName("Aranciopoli");
        cityCreateRequestDto.setProvince("AL");
        cityCreateRequestDto.setRegion("Piemonte");
        cityCreateRequestDto.setPopulation(500);

        String body = objectMapper.writeValueAsString(cityCreateRequestDto);

        mockMvc.perform(post("/v1.0/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }


    @Test
    void shouldUpdateCity() throws Exception {

        UpdateCityRequestDto updateCityRequestDto = new UpdateCityRequestDto();
        updateCityRequestDto.setPopulation(500);

        String body = objectMapper.writeValueAsString(updateCityRequestDto);

        mockMvc.perform(put("/v1.0/cities/58091")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }


    @Test
    void shouldDeleteACity() throws Exception {
        mockMvc.perform(delete("/v1.0/cities/5809"))
                .andExpect(status().isNoContent());
    }





    @Test
    void testGetCitiesWithPopulationGreaterThan() throws Exception {

        when(cityService.getCitiesWithPopulationGreaterThan(2000000)).thenReturn(cityModels);

        mockMvc.perform(post("/v1.0/cities/search-by-inhabitants")
                        .param("population", "2000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Roma"));

        verify(cityService).getCitiesWithPopulationGreaterThan(2000000);

        assertEquals(cityModels.size(), 1);
        assertEquals(cityModels.get(0).getName(), "Roma");
    }


    @Test
    void testGetCitiesByRegionWithPopulationGreaterThan() throws Exception {

        when(cityService.getCitiesByRegionWithPopulationGreaterThan("Lazio", 2000000)).thenReturn(cityModels);

        mockMvc.perform(post("/v1.0/cities/region/Lazio/search-by-inhabitants")
                        .param("population", "2000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Roma"));

        verify(cityService).getCitiesByRegionWithPopulationGreaterThan("Lazio", 2000000);

        assertEquals(cityModels.size(), 1);
        assertEquals(cityModels.get(0).getName(), "Roma");
    }

    @Test
    public void testGetCitiesByProvinceWithPopulationGreaterThanAndNameNot() throws Exception {

        when(cityService.getCitiesByProvinceWithPopulationGreaterThan("RM", 2000000)).thenReturn(cityModels);

        mockMvc.perform(post("/v1.0/cities/province/RM/search-by-inhabitants")
                        .param("population", "2000000")
                        .param("excludedCapitalProvince", "Roma")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Roma"));

        verify(cityService).getCitiesByProvinceWithPopulationGreaterThan("RM", 2000000);

        assertEquals(cityModels.size(), 1);
        assertEquals(cityModels.get(0).getName(), "Roma");
    }






}


