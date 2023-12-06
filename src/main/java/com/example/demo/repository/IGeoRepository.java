package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.GeoEntity;

public interface IGeoRepository extends JpaRepository<GeoEntity, Long>{

}
