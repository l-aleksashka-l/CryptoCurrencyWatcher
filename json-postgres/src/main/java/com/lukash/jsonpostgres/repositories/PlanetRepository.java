package com.lukash.jsonpostgres.repositories;

import com.lukash.jsonpostgres.entities.Planet;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<Planet, Long> {
}
