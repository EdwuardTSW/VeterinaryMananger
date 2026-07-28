package com.mananger_veterinary.vetman.domain.repository;

import com.mananger_veterinary.vetman.domain.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    List<Pet> findAll();

    Optional<Pet> findById(Integer id);

    List<Pet> findByNameContaining(String name);

    Pet save(Pet pet);

    void deleteById(Integer id);
}