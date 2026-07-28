package com.mananger_veterinary.vetman.domain.service;

import com.mananger_veterinary.vetman.domain.Pet;
import com.mananger_veterinary.vetman.domain.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Optional<Pet> findById(Integer id) {
        return petRepository.findById(id);
    }

    public List<Pet> findByNameContaining(String name) {
        return petRepository.findByNameContaining(name);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public boolean deleteById(Integer id) {
        if (petRepository.findById(id).isEmpty()) {
            return false;
        }

        petRepository.deleteById(id);
        return true;
    }
}