package com.example.demo.repository;

import com.example.demo.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityRepository extends JpaRepository<CityEntity, Long> {

    @Modifying
    @Query("update city set population = :population where id =:id")
    void updatePopulationById(Integer population, Long id);
    @Query(nativeQuery = true, value = "select * from city where name = :name")
    List<CityEntity> findByName(String name);

    @Override
    <S extends CityEntity> S save(S entity);
    @Query
    List<CityEntity> findByPopulationGreaterThan(Integer population);

    @Query
    List<CityEntity> findByRegionAndPopulationGreaterThan(String region, Integer population);

    @Query
    List<CityEntity> findByProvinceAndPopulationGreaterThan(String province, Integer population);

    @Query
    boolean existsByProvinceIgnoreCase(String province);
    @Query
    boolean existsByRegionIgnoreCase(String region);

    @Query
    List<CityEntity> findByProvince(String province);
}
