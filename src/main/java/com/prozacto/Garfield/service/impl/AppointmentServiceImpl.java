package com.prozacto.Garfield.service.impl;

import com.prozacto.Garfield.domain.dto.AppointmentDto;
import com.prozacto.Garfield.domain.request.AppointmentRequest;
import com.prozacto.Garfield.exception.AppointmentException;
import com.prozacto.Garfield.model.Appointment;
import com.prozacto.Garfield.model.UserProfile;
import com.prozacto.Garfield.repository.AppointmentRepository;
import com.prozacto.Garfield.repository.UserProfileRepository;
import com.prozacto.Garfield.service.AppointmentService;
import com.prozacto.Garfield.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  PatientService patientService,
                                  UserProfileRepository userProfileRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public AppointmentDto getAppointment(String appointmentId) {
        Appointment appointment =
                appointmentRepository.getAppointment(Integer.valueOf(appointmentId));
        return new AppointmentDto(String.valueOf(appointment.getId()),
                                  userProfileRepository
                                          .getUserProfileById(appointment.getPatientId())
                                          .getUserEmail(),
                                  userProfileRepository
                                          .getUserProfileById(appointment.getDoctorId())
                                          .getUserEmail(),
                                  appointment.getComments(),
                                  appointment.getStatus(),
                                  appointment.getAppointmentTime(),
                                  appointment.getAppointmentDuration());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByDoctor(String doctorEmail)
            throws AppointmentException {
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        UserProfile doctor = userProfileRepository.getUserProfileByUserEmail(doctorEmail);
        if (doctor == null) {
            throw new AppointmentException("Doctor is not registered");
        }
        for (Appointment appointment : appointmentRepository
                .getAppointmentsByDoctorId(doctor.getId())) {
            long appointmentTime;
            long appointmentDuration;
            if (appointment.getAppointmentTime() == null){
                appointmentTime = -1l;
            } else {
                appointmentTime = appointment.getAppointmentTime();
            }
            if(appointment.getAppointmentDuration() == null){
                appointmentDuration = -1l;
            } else {
                appointmentDuration = appointment.getAppointmentDuration();
            }
            appointmentDtos.add(new AppointmentDto(String.valueOf(appointment.getId()),
                                                   userProfileRepository
                                                           .getUserProfileById(
                                                                   appointment.getPatientId())
                                                           .getUserEmail(),
                                                   userProfileRepository
                                                           .getUserProfileById(
                                                                   appointment.getDoctorId())
                                                           .getUserEmail(),
                                                   appointment.getComments(),
                                                   appointment.getStatus(),
                                                   appointmentTime,
                                                   appointmentDuration));
        }
        return appointmentDtos;
    }

    @Override
    public void requestAppointment(AppointmentRequest appointment) throws AppointmentException {
        UserProfile patient =
                userProfileRepository.getUserProfileByUserEmail(appointment.getPatientEmail());
        UserProfile doctor =
                userProfileRepository.getUserProfileByUserEmail(appointment.getDoctorEmail());
        if (patient == null || doctor == null) {
            throw new AppointmentException("Either Patient or doctor is not registered.");
        }
        if (!patientService.checkPatientReport(patient.getId())) {
            throw new AppointmentException(
                    "No patient record present. Please upload patient record first.");
        }
        appointmentRepository.insert(patient.getId(),
                                     doctor.getId(),
                                     String.valueOf(System.currentTimeMillis()),
                                     "REQUESTED",
                                     appointment.getComments());
    }

    @Override
    public void updateAppointment(String appointmentId, String status) {
        appointmentRepository.setStatus(status, Integer.valueOf(appointmentId));
    }

    @Override
    public void approveAppointment(int appointmentId, long appointmentTime,
                                   long appointmentDuration) throws AppointmentException {
        if (appointmentTime > System.currentTimeMillis() &&
            checkTimeIsFree(appointmentId, appointmentTime, appointmentDuration)) {
            appointmentRepository
                    .setAppointment(appointmentId, appointmentTime, appointmentDuration);
        } else {
            throw new AppointmentException("Doctor is not free. Please choose another time");
        }
    }

    private boolean checkTimeIsFree(int appointmentId, long time, long duration) {
        int doctorId = appointmentRepository.getAppointment(appointmentId).getDoctorId();
        List<Appointment> appointments = appointmentRepository.getAppointmentsByDoctorId(doctorId);
        List<List<Long>> busyTimes = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentTime() == null &&
                appointment.getAppointmentDuration() == null) {
                continue;
            }
            List<Long> list = new ArrayList<>();
            list.add(appointment.getAppointmentTime());
            list.add(appointment.getAppointmentTime() + appointment.getAppointmentDuration());
            busyTimes.add(list);
        }
        if (busyTimes.isEmpty()) {
            return true;
        }
        busyTimes.sort((o1, o2) -> (int) (o1.get(0) - o2.get(0)));
        for (int i = 0; i < busyTimes.size(); i++) {
            if (time > busyTimes.get(i).get(1) &&
                time + duration * 60 * 1000 < busyTimes.get(i + 1).get(0) &&
                i + 1 != busyTimes.size()) {
                return true;
            }
        }
        return false;
    }
}
