package com.mananger_veterinary.vetman.domain.repository;

import com.mananger_veterinary.vetman.domain.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {

    List<Appointment> findAll();

    Optional<Appointment> findById(Integer id);

    List<Appointment> findByReasonContaining(String reason);

    Appointment save(Appointment appointment);

    void deleteById(Integer id);
}