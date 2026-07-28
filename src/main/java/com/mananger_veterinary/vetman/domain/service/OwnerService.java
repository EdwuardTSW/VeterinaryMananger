package com.mananger_veterinary.vetman.domain.service;

import com.mananger_veterinary.vetman.domain.Owner;
import com.mananger_veterinary.vetman.domain.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> findById(Integer id) {
        return ownerRepository.findById(id);
    }

    public List<Owner> findByNameContaining(String name) {
        return ownerRepository.findByNameContaining(name);
    }

    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    public boolean deleteById(Integer id) {
        if (ownerRepository.findById(id).isEmpty()) {
            return false;
        }

        ownerRepository.deleteById(id);
        return true;
    }
}