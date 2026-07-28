package com.mananger_veterinary.vetman.domain.service;

import com.mananger_veterinary.vetman.domain.Treatment;
import com.mananger_veterinary.vetman.domain.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public List<Treatment> findAll() {
        return treatmentRepository.findAll();
    }

    public Optional<Treatment> findById(Integer id) {
        return treatmentRepository.findById(id);
    }

    public List<Treatment> findByDescriptionContaining(String description) {
        return treatmentRepository.findByDescriptionContaining(description);
    }

    public Treatment save(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public boolean deleteById(Integer id) {
        if (treatmentRepository.findById(id).isEmpty()) {
            return false;
        }

        treatmentRepository.deleteById(id);
        return true;
    }
}