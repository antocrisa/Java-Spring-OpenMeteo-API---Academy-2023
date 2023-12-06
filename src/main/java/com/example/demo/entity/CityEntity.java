package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "region", "province"})})
@Entity(name = "city")
public class CityEntity {

    @Id
    @Column(name = "id", unique = true)
    @JsonProperty(value = "istat")
    private Long id;

    @Column(name = "name")
    @JsonProperty(value = "comune")
    private String name;

    @Column(name = "region")
    @JsonProperty(value = "regione")
    private String region;

    @Column(name = "province")
    @JsonProperty(value = "provincia")
    private String province;

    @Column(name = "population")
    @JsonProperty(value = "num_residenti")
    private Integer population;

    @OneToOne(mappedBy = "city", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private GeoEntity geo;
}