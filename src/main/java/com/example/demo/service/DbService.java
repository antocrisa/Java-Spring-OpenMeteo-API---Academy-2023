package com.example.demo.service;

import com.example.demo.entity.CityEntity;
import com.example.demo.entity.GeoEntity;
import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.repository.ICityRepository;
import com.example.demo.repository.IGeoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class DbService {

    private final ICityRepository iCityRepository;
    private final IGeoRepository iGeoRepository;
    private final ObjectMapper objectMapper;
    
    @PostConstruct
    public void init() throws IOException {
        importCitiesFromJsonFile("italy_cities.json");
        importGeoFromJsonFile("italy_geo.json");
    }


    @Transactional
    public void importCitiesFromJsonFile(String jsonFilePath) {
        try {
            List<CityEntity> cities = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<CityEntity>>() {});

            iCityRepository.saveAll(cities);

        } catch (Exception e) {
            throw generateInternalError(e);
        }
    }

     @Transactional
    public void importGeoFromJsonFile(String jsonFilePath) {
        try {
            List<GeoEntity> geo = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<GeoEntity>>() {});

            iGeoRepository.saveAll(geo);

        } catch (Exception e) {
            throw generateInternalError(e);
        }
    }



    private InternalErrorException generateInternalError(Exception e) {
        return new InternalErrorException("Error reading file", e, ErrorCode.ERROR_READING_FILE);
    }


       



}
