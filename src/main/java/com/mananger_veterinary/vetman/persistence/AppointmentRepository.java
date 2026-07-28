package com.mananger_veterinary.vetman.persistence;

import com.mananger_veterinary.vetman.domain.Appointment;
import com.mananger_veterinary.vetman.persistence.crud.AppointmentCrudRepository;
import com.mananger_veterinary.vetman.persistence.entity.AppointmentEntity;
import com.mananger_veterinary.vetman.persistence.mapper.AppointmentMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentRepository implements com.mananger_veterinary.vetman.domain.repository.AppointmentRepository {

    private final AppointmentCrudRepository appointmentCrudRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentRepository(
            AppointmentCrudRepository appointmentCrudRepository,
            AppointmentMapper appointmentMapper
    ) {
        this.appointmentCrudRepository = appointmentCrudRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();

        appointmentCrudRepository.findAll()
                .forEach(entity -> appointments.add(appointmentMapper.toDomain(entity)));

        return appointments;
    }

    @Override
    public Optional<Appointment> findById(Integer id) {
        return appointmentCrudRepository.findById(id)
                .map(appointmentMapper::toDomain);
    }

    @Override
    public List<Appointment> findByReasonContaining(String reason) {
        return appointmentCrudRepository.findByReasonContainingIgnoreCase(reason)
                .stream()
                .map(appointmentMapper::toDomain)
                .toList();
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = appointmentMapper.toEntity(appointment);
        AppointmentEntity savedEntity = appointmentCrudRepository.save(entity);

        return appointmentMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Integer id) {
        appointmentCrudRepository.deleteById(id);
    }
}