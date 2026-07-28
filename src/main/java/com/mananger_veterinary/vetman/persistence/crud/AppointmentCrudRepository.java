package com.mananger_veterinary.vetman.persistence.crud;

import com.mananger_veterinary.vetman.persistence.entity.AppointmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentCrudRepository extends CrudRepository<AppointmentEntity, Integer> {
}