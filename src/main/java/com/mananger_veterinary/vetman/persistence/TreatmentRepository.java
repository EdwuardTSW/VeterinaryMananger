package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.domain.Treatment;
import org.springframework.data.repository.CrudRepository;

public interface TreatmentRepository extends CrudRepository<Treatment, Integer> {
}