package com.mananger_veterinary.vetman.persistence.crud;

import com.mananger_veterinary.vetman.persistence.entity.TreatmentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TreatmentCrudRepository extends CrudRepository<TreatmentEntity, Integer> {

    List<TreatmentEntity> findByDescriptionContainingIgnoreCase(String description);
}