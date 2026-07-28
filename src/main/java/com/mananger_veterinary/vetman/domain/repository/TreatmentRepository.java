package com.mananger_veterinary.vetman.domain.repository;

import com.mananger_veterinary.vetman.domain.Treatment;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository {

    List<Treatment> findAll();

    Optional<Treatment> findById(Integer id);

    List<Treatment> findByDescriptionContaining(String description);

    Treatment save(Treatment treatment);

    void deleteById(Integer id);
}