package com.mananger_veterinary.vetman.domain.service;

import com.mananger_veterinary.vetman.domain.Pet;
import com.mananger_veterinary.vetman.domain.exception.DuplicatePetException;
import com.mananger_veterinary.vetman.domain.repository.OwnerRepository;
import com.mananger_veterinary.vetman.domain.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetService(
            PetRepository petRepository,
            OwnerRepository ownerRepository
    ) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
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
        if (pet.getName() == null || pet.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        }

        if (pet.getOwner() == null || pet.getOwner().getId() == null) {
            throw new IllegalArgumentException("Debe indicar el ID de un dueño");
        }

        Integer ownerId = pet.getOwner().getId();

        if (ownerRepository.findById(ownerId).isEmpty()) {
            throw new IllegalArgumentException("El dueño indicado no existe");
        }

        if (petRepository.existsByOwnerIdAndName(ownerId, pet.getName())) {
            throw new DuplicatePetException(
                    "El dueño ya tiene una mascota registrada con ese nombre"
            );
        }

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