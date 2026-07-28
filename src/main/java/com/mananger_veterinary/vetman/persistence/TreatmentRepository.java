package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.domain.Treatment;
import com.mananger_veterinary.vetman.persistence.crud.TreatmentCrudRepository;
import com.mananger_veterinary.vetman.persistence.entity.TreatmentEntity;
import com.mananger_veterinary.vetman.persistence.mapper.TreatmentMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TreatmentRepository implements com.mananger_veterinary.vetman.domain.repository.TreatmentRepository {

    private final TreatmentCrudRepository treatmentCrudRepository;
    private final TreatmentMapper treatmentMapper;

    public TreatmentRepository(
            TreatmentCrudRepository treatmentCrudRepository,
            TreatmentMapper treatmentMapper
    ) {
        this.treatmentCrudRepository = treatmentCrudRepository;
        this.treatmentMapper = treatmentMapper;
    }

    @Override
    public List<Treatment> findAll() {
        List<Treatment> treatments = new ArrayList<>();

        treatmentCrudRepository.findAll()
                .forEach(entity -> treatments.add(treatmentMapper.toDomain(entity)));

        return treatments;
    }

    @Override
    public Optional<Treatment> findById(Integer id) {
        return treatmentCrudRepository.findById(id)
                .map(treatmentMapper::toDomain);
    }

    @Override
    public List<Treatment> findByDescriptionContaining(String description) {
        return treatmentCrudRepository.findByDescriptionContainingIgnoreCase(description)
                .stream()
                .map(treatmentMapper::toDomain)
                .toList();
    }

    @Override
    public Treatment save(Treatment treatment) {
        TreatmentEntity entity = treatmentMapper.toEntity(treatment);
        TreatmentEntity savedEntity = treatmentCrudRepository.save(entity);

        return treatmentMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Integer id) {
        treatmentCrudRepository.deleteById(id);
    }
}