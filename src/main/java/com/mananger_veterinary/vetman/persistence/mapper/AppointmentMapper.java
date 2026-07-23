package com.mananger_veterinary.vetman.persistence.mapper;

import com.mananger_veterinary.vetman.domain.Appointment;
import com.mananger_veterinary.vetman.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PetMapper.class)
public interface AppointmentMapper {

    Appointment toDomain(AppointmentEntity entity);

    AppointmentEntity toEntity(Appointment domain);
}