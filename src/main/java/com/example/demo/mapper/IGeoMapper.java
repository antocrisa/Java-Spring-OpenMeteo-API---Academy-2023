package com.example.demo.mapper;

import com.example.demo.dto.geo.GeoResponseDto;
import com.example.demo.entity.GeoEntity;
import com.example.demo.model.GeoModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface IGeoMapper {

    GeoResponseDto responseFromModel(GeoModel geoModel);

    GeoModel modelFromEntity(GeoEntity geoEntity);

}
