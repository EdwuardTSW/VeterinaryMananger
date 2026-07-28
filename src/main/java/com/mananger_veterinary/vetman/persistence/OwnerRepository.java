package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.domain.Owner;
import com.mananger_veterinary.vetman.persistence.crud.OwnerCrudRepository;
import com.mananger_veterinary.vetman.persistence.entity.OwnerEntity;
import com.mananger_veterinary.vetman.persistence.mapper.OwnerMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OwnerRepository implements com.mananger_veterinary.vetman.domain.repository.OwnerRepository {

    private final OwnerCrudRepository ownerCrudRepository;
    private final OwnerMapper ownerMapper;

    public OwnerRepository(OwnerCrudRepository ownerCrudRepository, OwnerMapper ownerMapper) {
        this.ownerCrudRepository = ownerCrudRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = new ArrayList<>();

        ownerCrudRepository.findAll()
                .forEach(entity -> owners.add(ownerMapper.toDomain(entity)));

        return owners;
    }

    @Override
    public Optional<Owner> findById(Integer id) {
        return ownerCrudRepository.findById(id)
                .map(ownerMapper::toDomain);
    }

    @Override
    public List<Owner> findByNameContaining(String name) {
        return ownerCrudRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ownerMapper::toDomain)
                .toList();
    }

    @Override
    public Owner save(Owner owner) {
        OwnerEntity entity = ownerMapper.toEntity(owner);
        OwnerEntity savedEntity = ownerCrudRepository.save(entity);

        return ownerMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Integer id) {
        ownerCrudRepository.deleteById(id);
    }
}