package com.prozacto.Garfield.domain.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppointmentRequest {
    private String patientEmail;
    private String doctorEmail;
    private String comments;
}
