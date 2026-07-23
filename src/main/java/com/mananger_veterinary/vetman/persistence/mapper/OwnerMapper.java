package com.mananger_veterinary.vetman.persistence.mapper;

import com.mananger_veterinary.vetman.domain.Owner;
import com.mananger_veterinary.vetman.persistence.entity.OwnerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    Owner toDomain(OwnerEntity entity);

    OwnerEntity toEntity(Owner domain);
}