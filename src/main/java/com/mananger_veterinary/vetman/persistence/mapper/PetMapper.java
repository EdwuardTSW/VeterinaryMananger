package com.mananger_veterinary.vetman.persistence.mapper;

import com.mananger_veterinary.vetman.domain.Pet;
import com.mananger_veterinary.vetman.persistence.entity.PetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OwnerMapper.class)
public interface PetMapper {

    Pet toDomain(PetEntity entity);

    PetEntity toEntity(Pet domain);
}