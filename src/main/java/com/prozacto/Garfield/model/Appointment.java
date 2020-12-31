package com.prozacto.Garfield.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 5340712953312355234L;

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "doctor_id")
    private int doctorId;

    @Column(name = "creation_time")
    private String creationTime;

    @Column(name = "status")
    private String status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "appointment_time")
    private Long appointmentTime;

    @Column(name = "appointment_duration")
    private Long appointmentDuration;
}
