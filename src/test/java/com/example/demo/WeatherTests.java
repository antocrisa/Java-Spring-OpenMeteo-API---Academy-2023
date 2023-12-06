package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.service.WeatherService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherTests {



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;


    CityModel cityModel = new CityModel();
    WeatherModel weatherModel = new WeatherModel();
    AvgModel avgModel = new AvgModel();

    HourlyModel hourlyData = new HourlyModel();

    @BeforeAll
    void createMock (){

        CityModel cityModel = new CityModel();
        cityModel.setName("Roma");
        cityModel.setId((long) 58091);

        GeoModel geoModel = new GeoModel();
        geoModel.setLat(BigDecimal.valueOf(41.89277044));
        geoModel.setLng(BigDecimal.valueOf(12.48366723));
        geoModel.setId((long) 58091);
        geoModel.setName("Roma");

        cityModel.setProvince("RM");
        cityModel.setRegion("Lazio");
        cityModel.setPopulation(2638842);
        cityModel.setGeo(geoModel);

        weatherModel.setLatitude(BigDecimal.valueOf(41.89277044));
        weatherModel.setLongitude(BigDecimal.valueOf(12.48366723));

        hourlyData.setLocalDateTime(List.of(LocalDateTime.now()));
        hourlyData.setTemperature(List.of(20.0));

        weatherModel.setHourly(hourlyData);

        avgModel.setAverageTemperature(20.0);
        avgModel.setStartDate(LocalDate.now().minusDays(5));
        avgModel.setEndDate(LocalDate.now());
    }

    @Test
    void testGetWeatherForCity() throws Exception {

        when(weatherService.readWeather(58091L)).thenReturn(weatherModel);


        mockMvc.perform(post("/v1.0/weather/search/city/58091")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(BigDecimal.valueOf(41.89277044)));

        verify(weatherService).readWeather(58091L);


    }



    @Test
    void testGetAvgTemperatureForCity() throws Exception {


        when(weatherService.getAvgTemperatureByCityAndNumberOfDays(58091L, 5)).thenReturn(avgModel);


        mockMvc.perform(post("/v1.0/weather/search/city/58091/average-temperature")
                        .param("days", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageTemperature").value(20.0));

        verify(weatherService).getAvgTemperatureByCityAndNumberOfDays(58091L, 5);


    }


    @Test
    void testGetAvgTemperatureForProvince() throws Exception {

        when(weatherService.getAvgTemperatureByProvince(LocalDate.now().minusDays(1), "RM")).thenReturn(avgModel);

        mockMvc.perform(post("/v1.0/weather/search/province/RM/average-temperature")
                        .param("date", String.valueOf(LocalDate.now().minusDays(1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageTemperature").value(20.0));

        verify(weatherService).getAvgTemperatureByProvince(LocalDate.now().minusDays(1), "RM");


    }

}