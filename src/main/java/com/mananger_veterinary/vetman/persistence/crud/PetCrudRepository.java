package com.mananger_veterinary.vetman.persistence.crud;

import com.mananger_veterinary.vetman.persistence.entity.PetEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PetCrudRepository extends CrudRepository<PetEntity, Integer> {

    List<PetEntity> findByNameContainingIgnoreCase(String name);
}