package com.example.demo.service;

import com.example.demo.entity.GeoEntity;
import com.example.demo.enumeration.ErrorCode;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.InternalErrorException;
import com.example.demo.mapper.IGeoMapper;
import com.example.demo.model.GeoModel;
import com.example.demo.repository.IGeoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GeoService {



    private final IGeoRepository iGeoRepository;
    private final IGeoMapper iGeoMapper;

    public GeoModel readCoordinate(Long id) {
        Optional<GeoEntity> geoEntity;
        try {
            geoEntity = iGeoRepository.findById(id);
        } catch (Exception e) {
            throw generateInternalError(e);
        }
        return geoEntity
                .map(iGeoMapper::modelFromEntity)
                .orElseThrow(() -> generateEntityNotFound(id));
    }



    private InternalErrorException generateInternalError(Exception e) {
        return new InternalErrorException("Something went wrong", e, ErrorCode.INTERNAL_ERROR);
    }

    private EntityNotFoundException generateEntityNotFound(Long id) {
        return new EntityNotFoundException("ID not found" + id, ErrorCode.DATA_NOT_FOUND);
    }





}
