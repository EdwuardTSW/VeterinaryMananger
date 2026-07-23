package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.persistence.entity.AppointmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<AppointmentEntity, Integer> {
}