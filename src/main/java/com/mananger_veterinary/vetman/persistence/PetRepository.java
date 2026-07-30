package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.domain.Pet;
import com.mananger_veterinary.vetman.persistence.crud.PetCrudRepository;
import com.mananger_veterinary.vetman.persistence.entity.PetEntity;
import com.mananger_veterinary.vetman.persistence.mapper.PetMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PetRepository implements com.mananger_veterinary.vetman.domain.repository.PetRepository {

    private final PetCrudRepository petCrudRepository;
    private final PetMapper petMapper;

    public PetRepository(PetCrudRepository petCrudRepository, PetMapper petMapper) {
        this.petCrudRepository = petCrudRepository;
        this.petMapper = petMapper;
    }

    @Override
    public List<Pet> findAll() {
        List<Pet> pets = new ArrayList<>();

        petCrudRepository.findAll()
                .forEach(entity -> pets.add(petMapper.toDomain(entity)));

        return pets;
    }

    @Override
    public Optional<Pet> findById(Integer id) {
        return petCrudRepository.findById(id)
                .map(petMapper::toDomain);
    }

    @Override
    public List<Pet> findByNameContaining(String name) {
        return petCrudRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(petMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByOwnerIdAndName(Integer ownerId, String name) {
        return petCrudRepository.existsByOwner_IdAndNameIgnoreCase(ownerId, name);
    }

    @Override
    public Pet save(Pet pet) {
        PetEntity entity = petMapper.toEntity(pet);
        PetEntity savedEntity = petCrudRepository.save(entity);

        return petMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Integer id) {
        petCrudRepository.deleteById(id);
    }
}