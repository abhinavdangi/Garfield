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
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>,
        JpaSpecificationExecutor<Appointment> {

    @Query(value = "SELECT A from Appointment A where A.id = :id")
    Appointment getAppointment(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Appointment A set A.status = :status where A.id = :id")
    void setStatus(@Param("status") String status, @Param("id") int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO appointment "
                                       + "(patient_id, doctor_id, creation_time, status, comments)"
                                       + " VALUES ( :patientId, :doctorId, :creationTime, :status, :comments)")
    void insert(@Param("patientId") int patientId,
                @Param("doctorId") int doctorId,
                @Param("creationTime") String creationTime,
                @Param("status") String status,
                @Param("comments") String comments
               );

    @Query(value = "SELECT A from Appointment A where A.doctorId = :doctorId")
    List<Appointment> getAppointmentsByDoctorId(int doctorId);

    @Query(value = "SELECT A from Appointment A")
    List<Appointment> getAppointments();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Appointment A set "
                   + "A.appointmentTime = :appointmentTime,"
                   + "A.appointmentDuration = :appointmentDuration,"
                   + "A.status = 'SCHEDULED' "
                   + "where A.id = :id")
    void setAppointment(@Param("id") int id,
                        @Param("appointmentTime") Long appointmentTime,
                        @Param("appointmentDuration") Long appointmentDuration);
}

