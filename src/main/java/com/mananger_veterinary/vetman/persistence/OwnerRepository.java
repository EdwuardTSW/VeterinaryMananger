package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.persistence.entity.OwnerEntity;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<OwnerEntity, Integer> {
}