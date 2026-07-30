package com.mananger_veterinary.vetman.domain.repository;

import com.mananger_veterinary.vetman.domain.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {

    List<Owner> findAll();

    Optional<Owner> findById(Integer id);

    Optional<Owner> findByEmail(String email);

    List<Owner> findByNameContaining(String name);

    Owner save(Owner owner);

    void deleteById(Integer id);
}