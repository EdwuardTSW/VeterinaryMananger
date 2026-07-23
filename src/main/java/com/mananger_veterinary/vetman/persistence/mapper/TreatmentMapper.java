package com.mananger_veterinary.vetman.persistence.mapper;

import com.mananger_veterinary.vetman.domain.Treatment;
import com.mananger_veterinary.vetman.persistence.entity.TreatmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AppointmentMapper.class)
public interface TreatmentMapper {

    Treatment toDomain(TreatmentEntity entity);

    TreatmentEntity toEntity(Treatment domain);
}