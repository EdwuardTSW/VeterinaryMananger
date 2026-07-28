package com.mananger_veterinary.vetman.domain.service;

import com.mananger_veterinary.vetman.domain.Appointment;
import com.mananger_veterinary.vetman.domain.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findById(Integer id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> findByReasonContaining(String reason) {
        return appointmentRepository.findByReasonContaining(reason);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public boolean deleteById(Integer id) {
        if (appointmentRepository.findById(id).isEmpty()) {
            return false;
        }

        appointmentRepository.deleteById(id);
        return true;
    }
}