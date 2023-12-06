package com.example.demo.service;

import com.example.demo.constant.Path;
import com.example.demo.dto.weather.WeatherResponseDto;
import com.example.demo.entity.GeoEntity;
import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.mapper.ICityMapper;
import com.example.demo.mapper.IWeatherMapper;
import com.example.demo.model.AvgModel;
import com.example.demo.model.CityModel;
import com.example.demo.model.WeatherModel;
import com.example.demo.repository.ICityRepository;
import com.example.demo.repository.IGeoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;

    private final ICityRepository iCityRepository;
    private final IWeatherMapper iWeatherMapper;
    private final ICityMapper iCityMapper;
    private final IGeoRepository iGeoRepository;


    public WeatherModel readWeather(Long id) {

        Optional<GeoEntity> geoEntity;
        WeatherResponseDto weatherResponseDto;
        HttpEntity<Void> httpEntity = new HttpEntity<>(null);
        String uri = "";

        try {
            geoEntity = iGeoRepository.findById(id);
        } catch (Exception e) {
            throw generateInternalError(e);
        }

        if (geoEntity.isPresent()) {
            uri = UriComponentsBuilder.fromUriString(Path.API_METEO)
                    .queryParam("latitude", geoEntity.get().getLat())
                    .queryParam("longitude", geoEntity.get().getLng())
                    .queryParam("hourly", "temperature_2m,weather_code")
                    .build().toUriString();

        }

        try {
            weatherResponseDto = this.restTemplate.exchange(uri,
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<WeatherResponseDto>() {
                    }).getBody();

        } catch (Exception e) {
            throw generateInternalError(e);
        }

        assert weatherResponseDto != null;
        weatherResponseDto.getHourly().convertWeatherCodesToDescriptions();

        return iWeatherMapper.modelFromResponse(weatherResponseDto);

    }


    public AvgModel getAvgTemperatureByCityAndNumberOfDays(Long id, int days) {

        WeatherResponseDto weatherResponseDto;
        HttpEntity<Void> httpEntity = new HttpEntity<>(null);
        String uri = "";

        Optional<GeoEntity> geoEntity;
        try {
            geoEntity = iGeoRepository.findById(id);
        } catch (Exception e) {
            throw generateInternalError(e);
        }

        if (geoEntity.isPresent()) {
            uri = UriComponentsBuilder.fromUriString(Path.API_METEO)
                    .queryParam("latitude", geoEntity.get().getLat())
                    .queryParam("longitude", geoEntity.get().getLng())
                    .queryParam("hourly", "temperature_2m")
                    .queryParam("past_days", days)
                    .queryParam("forecast_days", 0)
                    .build().toUriString();

        }


        try {
            weatherResponseDto = this.restTemplate.exchange(uri,
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<WeatherResponseDto>() {
                    }).getBody();

        } catch (Exception e) {
            throw generateInternalError(e);
        }


        assert weatherResponseDto != null;
        List<Double> temperatures = weatherResponseDto.getHourly().getTemperature();
        AvgModel avgModel = new AvgModel();
        avgModel.setAverageTemperature(avg(temperatures));
        avgModel.setStartDate(LocalDate.now().minusDays(days));
        avgModel.setEndDate(LocalDate.now());

        return avgModel;
    }

    public AvgModel getAvgTemperatureByProvince(LocalDate date, String province){
        List<Double> dailyAverages = new ArrayList<>();


        try {
            iCityRepository.existsByProvinceIgnoreCase(province);
        } catch (Exception e) {
            throw generateDataNotFound(province);
        }

        List<CityModel> cityModels;
        try {
            cityModels = iCityRepository.findByProvince(province).stream()
                    .map(iCityMapper::modelFromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw generateInternalError(e);
        }

        String uri;
        for (CityModel city : cityModels) {
            uri = UriComponentsBuilder.fromUriString(Path.API_METEO)
                    .queryParam("latitude", city.getGeo().getLat())
                    .queryParam("longitude",city.getGeo().getLng())
                    .queryParam("hourly", "temperature_2m")
                    .queryParam("start_date", date)
                    .queryParam("end_date", date)
                    .build().toUriString();

            HttpEntity<Void> httpEntity = new HttpEntity<>(null);
            WeatherResponseDto weatherResponseDto;
            try {
                 weatherResponseDto= this.restTemplate.exchange(uri,
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<WeatherResponseDto>() {
                        }).getBody();

            } catch (Exception e) {
                throw generateInternalError(e);
            }

            assert weatherResponseDto != null;
            double dailyAverage = avg(weatherResponseDto.getHourly().getTemperature());
            dailyAverages.add(dailyAverage);
            }

        double averageTemperature = avg(dailyAverages);
        AvgModel avgModel = new AvgModel();
        avgModel.setAverageTemperature(averageTemperature);
        avgModel.setStartDate(date);
        avgModel.setEndDate(date);

        return avgModel;

    }

    private InternalErrorException generateInternalError(Exception e) {
        return new InternalErrorException("Something went wrong", e, ErrorCode.INTERNAL_ERROR);
    }

    private DataNotFoundException generateDataNotFound(String msg) {
        return new DataNotFoundException("Province not found : " + msg, ErrorCode.DATA_NOT_FOUND);
    }

    private double avg(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (Double value : values) {
            sum += value;
        }

        return sum / values.size();
    }

}
