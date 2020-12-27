package com.prozacto.Garfield.service.impl;

import com.prozacto.Garfield.domain.dto.AppointmentDto;
import com.prozacto.Garfield.domain.request.AppointmentRequest;
import com.prozacto.Garfield.model.Appointment;
import com.prozacto.Garfield.repository.AppointmentRepository;
import com.prozacto.Garfield.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public AppointmentDto getAppointment(String appointmentId) {
        return AppointmentDto.getAppointmentDto(
                appointmentRepository.getAppointment(Long.valueOf(appointmentId)));
    }

    @Override
    public List<AppointmentDto> getAppointmentsByDoctor(String doctorEmail) {
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        for (Appointment appointment : appointmentRepository.getAppointmentsByDoctorEmail(doctorEmail)) {
            appointmentDtos.add(AppointmentDto.getAppointmentDto(appointment));
        }
        return appointmentDtos;
    }

    @Override
    public void requestAppointment(AppointmentRequest appointment) {
        appointmentRepository.insert(appointment.getPatientEmail(),
                                     appointment.getDoctorEmail(),
                                     String.valueOf(System.currentTimeMillis()),
                                     "REQUESTED",
                                     appointment.getComments(),
                                     "",
                                     "");
    }

    @Override
    public void updateAppointment(String appointmentId, String status) {
        appointmentRepository.setStatus(status, Long.valueOf(appointmentId));
    }

    @Override
    public void approveAppointment(String appointmentId, String appointmentTime,
                                   String appointmentDuration) {
        // TODO: check if time is proper and doctor is free.
        appointmentRepository.setAppointment(Long.valueOf(appointmentId), appointmentTime,
                                             appointmentDuration);
    }
}
