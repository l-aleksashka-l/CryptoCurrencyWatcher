package com.lukash.jsonpostgres.repositories;

import com.lukash.jsonpostgres.entities.PersonWithPlanets;
import org.springframework.data.repository.CrudRepository;

public interface PersonWithPlanetsRepository extends CrudRepository<PersonWithPlanets,Long> {
}
