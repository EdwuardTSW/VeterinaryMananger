package com.mananger_veterinary.vetman.persistence.crud;

import com.mananger_veterinary.vetman.persistence.entity.TreatmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface TreatmentCrudRepository extends CrudRepository<TreatmentEntity, Integer> {
}