package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.domain.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
}