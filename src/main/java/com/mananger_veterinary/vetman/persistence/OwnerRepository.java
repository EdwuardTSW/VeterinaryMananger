package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.domain.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
}