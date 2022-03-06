package com.lukash.jsonpostgres.repositories;

import com.lukash.jsonpostgres.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
