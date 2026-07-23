package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.persistence.entity.TreatmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface TreatmentRepository extends CrudRepository<TreatmentEntity, Integer> {
}