package com.mananger_veterinary.vetman.persistence.crud;

import com.mananger_veterinary.vetman.persistence.entity.AppointmentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentCrudRepository extends CrudRepository<AppointmentEntity, Integer> {

    List<AppointmentEntity> findByReasonContainingIgnoreCase(String reason);
}