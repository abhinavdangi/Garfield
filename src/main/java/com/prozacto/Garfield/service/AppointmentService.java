package com.prozacto.Garfield.service;

import com.prozacto.Garfield.domain.dto.AppointmentDto;
import com.prozacto.Garfield.domain.request.AppointmentRequest;
import com.prozacto.Garfield.exception.AppointmentException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    AppointmentDto getAppointment(String appointmentId);

    List<AppointmentDto> getAppointmentsByDoctor(String doctorEmail);

    void requestAppointment(AppointmentRequest appointment) throws AppointmentException;

    void updateAppointment(String appointmentId, String status);

    void approveAppointment(String appointmentId, String appointmentTime,
                            String appointmentDuration);
}
