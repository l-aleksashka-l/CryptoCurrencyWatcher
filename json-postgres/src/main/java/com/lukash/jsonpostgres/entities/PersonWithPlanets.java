package com.lukash.jsonpostgres.entities;

import javax.persistence.*;

@Entity
@Table(schema="space",name="personWithPlanets")
public class PersonWithPlanets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lordName;
    private String planetName;

    public PersonWithPlanets() {
    }


    public PersonWithPlanets(String lordName, String planetName) {
        this.lordName = lordName;
        this.planetName = planetName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLordName() {
        return lordName;
    }

    public void setLordName(String lordName) {
        this.lordName = lordName;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }
}
