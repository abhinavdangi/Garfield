package com.prozacto.Garfield.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AppointmentDto {
    private String id;
    private String patientEmail;
    private String doctorEmail;
    private String comments;
    private String status;
    private long appointmentTime;
    private long appointmentDuration;
}
