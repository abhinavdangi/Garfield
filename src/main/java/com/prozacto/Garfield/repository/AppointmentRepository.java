package com.prozacto.Garfield.repository;

import com.prozacto.Garfield.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>,
        JpaSpecificationExecutor<Appointment> {

    @Query(value = "SELECT A from Appointment A where A.id = :id")
    Appointment getAppointment(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Appointment A set A.status = :status where A.id = :id")
    void setStatus(@Param("status") String status, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO appointment "
                                       + "(patient_email, doctor_email, creation_time, status, comments, appointment_time, appointment_duration)"
                                       + " VALUES ( :patientEmail, :doctorEmail, :creationTime, :status, :comments, :appointmentTime, :appointmentDuration)")
    void insert(@Param("patientEmail") String patientEmail,
                @Param("doctorEmail") String doctorEmail,
                @Param("creationTime") String creationTime,
                @Param("status") String status,
                @Param("comments") String comments,
                @Param("appointmentTime") String appointmentTime,
                @Param("appointmentDuration") String appointmentDuration
               );

    @Query(value = "SELECT A from Appointment A where A.doctorEmail = :doctorEmail")
    List<Appointment> getAppointmentsByDoctorEmail(String doctorEmail);

    @Query(value = "SELECT A from Appointment A")
    List<Appointment> getAppointments();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Appointment A set "
                   + "A.appointmentTime = :appointmentTime,"
                   + "A.appointmentDuration = :appointmentDuration,"
                   + "A.status = 'SCHEDULED' "
                   + "where A.id = :id")
    void setAppointment(@Param("id") Long id,
                        @Param("appointmentTime") String appointmentTime,
                        @Param("appointmentDuration") String appointmentDuration);
}

