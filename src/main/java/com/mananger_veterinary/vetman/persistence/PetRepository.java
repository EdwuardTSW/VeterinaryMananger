package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.persistence.entity.PetEntity;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<PetEntity, Integer> {
}