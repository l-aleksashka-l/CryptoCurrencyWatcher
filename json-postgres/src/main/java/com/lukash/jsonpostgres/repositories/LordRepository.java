package com.lukash.jsonpostgres.repositories;

import com.lukash.jsonpostgres.entities.Lord;
import org.springframework.data.repository.CrudRepository;

public interface LordRepository extends CrudRepository<Lord, Long> {
}
