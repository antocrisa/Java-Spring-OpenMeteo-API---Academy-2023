package com.example.demo.service;

import com.example.demo.entity.CityEntity;
import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.mapper.ICityMapper;
import com.example.demo.model.CityModel;
import com.example.demo.repository.ICityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CityService {

    private final ICityRepository iCityRepository;
    private final ICityMapper iCityMapper;

    public List<CityModel> findByName(String name) {

        List<CityEntity> cityEntities;

        try {
            cityEntities = iCityRepository.findByName(name);
        } catch (Exception e) {
            throw generateInternalError(e);
        }

        return cityEntities.stream()
                .map(iCityMapper::modelFromEntity)
                .collect(Collectors.toList());
    }

    public CityModel findById(Long id) {

        Optional<CityEntity> cityEntity;

        try {
            cityEntity = iCityRepository.findById(id);
        } catch (Exception e) {
            throw generateInternalError(e);
        }

        return cityEntity.map(iCityMapper::modelFromEntity)
                .orElseThrow(() -> generateEntityNotFound(id));
    }

    @Transactional
    public CityModel updatePopulationById(Long id, CityModel cityModel) {

        CityEntity cityEntity = new CityEntity();

        try {
            Optional<CityEntity> opt = iCityRepository.findById(id);

            if (opt.isPresent()) {
                cityEntity = opt.get();
                iCityRepository.updatePopulationById(cityModel.getPopulation(), id);
            }
        } catch (Exception e) {
            throw generateInternalError(e);
        }
        cityEntity.setPopulation(cityModel.getPopulation());
        return iCityMapper.modelFromEntity(cityEntity);
    }

    public CityModel createCity(CityModel cityModel) {

        CityEntity cityEntity;
        try {
            cityEntity = iCityRepository.save(iCityMapper.entityFromModel(cityModel));

        } catch (Exception e) {
            throw generateInternalError(e);
        }
        return iCityMapper.modelFromEntity(cityEntity);

    }

    public void deleteById(Long id) {

        Optional<CityEntity> deleted;
        try {
            deleted = iCityRepository.findById(id);

            if (deleted.isPresent()) {
                iCityRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw generateInternalError(e);
        }

    }


    public List<CityModel> getCitiesWithPopulationGreaterThan(Integer population) {

        try {
            return iCityRepository.findByPopulationGreaterThan(population).stream()
                    .map(iCityMapper::modelFromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw generateInternalError(e);
        }


    }

    public List<CityModel> getCitiesByRegionWithPopulationGreaterThan(String region, Integer population) {


        boolean exists;
        try {
            exists = iCityRepository.existsByRegionIgnoreCase(region);
        } catch (Exception e) {
            throw generateDataNotFound(region);
        }

        try {
            return iCityRepository.findByRegionAndPopulationGreaterThan(region, population).stream()
                    .map(iCityMapper::modelFromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw generateInternalError(e);
        }
    }

    public List<CityModel> getCitiesByProvinceWithPopulationGreaterThan(String province, Integer population) {


        boolean exists;
        try {
            exists = iCityRepository.existsByProvinceIgnoreCase(province);
        } catch (Exception e) {
            throw generateDataNotFound(province);
        }


        try {
            return iCityRepository.findByProvinceAndPopulationGreaterThan(province, population).stream()
                    .map(iCityMapper::modelFromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw generateInternalError(e);
        }
    }

    private InternalErrorException generateInternalError(Exception e) {
        return new InternalErrorException("Something went wrong", e, ErrorCode.INTERNAL_ERROR);
    }

    private EntityNotFoundException generateEntityNotFound(Long id) {
        return new EntityNotFoundException("ID not found : " + id, ErrorCode.DATA_NOT_FOUND);
    }

    private DataNotFoundException generateDataNotFound(String msg) {
        return new DataNotFoundException("No data found for: " + msg, ErrorCode.DATA_NOT_FOUND);
    }


}



