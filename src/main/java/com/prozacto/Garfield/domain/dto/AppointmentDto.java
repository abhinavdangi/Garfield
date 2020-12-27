package com.prozacto.Garfield.domain.dto;

import com.prozacto.Garfield.model.Appointment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppointmentDto {
    private String id;
    private String patientEmail;
    private String doctorEmail;
    private String comments;

    public static AppointmentDto getAppointmentDto(Appointment appointment){
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(String.valueOf(appointment.getId()));
        appointmentDto.setComments(appointment.getComments());
        appointmentDto.setDoctorEmail(appointment.getDoctorEmail());
        appointmentDto.setPatientEmail(appointment.getPatientEmail());
        return appointmentDto;
    }
}
