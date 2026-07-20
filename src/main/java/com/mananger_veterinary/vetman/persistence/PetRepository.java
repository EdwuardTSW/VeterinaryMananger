package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.domain.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Integer> {
}