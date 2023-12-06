package com.example.demo.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "geo")
public class GeoEntity {

    @Id
    @Column(name="id")
    @JsonProperty(value="istat")
    private Long id;

    @Column(name="name")
    @JsonProperty(value="comune")
    private String name;

    @Column(name="lng")
    @JsonProperty(value="lng")
    private BigDecimal lng;

    @Column(name="lat")
    @JsonProperty(value="lat")
    private BigDecimal lat;

    @OneToOne
    @JoinColumn(name = "id",referencedColumnName = "id")
    private CityEntity city;

    
}
