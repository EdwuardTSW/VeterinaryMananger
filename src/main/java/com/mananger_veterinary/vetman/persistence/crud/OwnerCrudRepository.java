package com.mananger_veterinary.vetman.persistence.crud;

import com.mananger_veterinary.vetman.persistence.entity.OwnerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OwnerCrudRepository extends CrudRepository<OwnerEntity, Integer> {

    List<OwnerEntity> findByNameContainingIgnoreCase(String name);

    Optional<OwnerEntity> findByEmailIgnoreCase(String email);
}