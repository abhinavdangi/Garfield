package com.prozacto.Garfield.controller;

import com.prozacto.Garfield.domain.HttpResponse;
import com.prozacto.Garfield.domain.request.AppointmentRequest;
import com.prozacto.Garfield.exception.AppointmentException;
import com.prozacto.Garfield.service.AppointmentService;
import com.prozacto.Garfield.utils.HttpResponseUtil;
import com.prozacto.Garfield.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(value = "/{appointment-id}")
    public void getAppointment(@PathVariable("appointment-id") String appointmentId,
                               HttpServletResponse response) throws IOException {
        HttpResponseUtil.returnResponse(response, new HttpResponse(
                HttpStatus.OK,
                MapperUtil.getObjectMapper()
                        .writeValueAsString(appointmentService.getAppointment(appointmentId))));
    }

    @GetMapping
    public void getAppointmentsByDoctor(@RequestParam(value = "doctor-email") String doctorEmail,
                                        HttpServletResponse response)
            throws IOException, AppointmentException {
        HttpResponseUtil.returnResponse(response, new HttpResponse(
                HttpStatus.OK,
                MapperUtil.getObjectMapper()
                        .writeValueAsString(appointmentService.getAppointmentsByDoctor(doctorEmail))));
    }

    @PostMapping
    public void requestAppointment(@RequestBody AppointmentRequest appointment,
                                   HttpServletResponse response)
            throws IOException, AppointmentException {
        appointmentService.requestAppointment(appointment);
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));
    }

    @PutMapping(value = "/{appointment-id}")
    public void updateAppointment(@PathVariable(value = "appointment-id") String appointmentId,
                                  @RequestParam(value = "status") String status,
                                  HttpServletResponse response) throws IOException {
        appointmentService.updateAppointment(appointmentId, status);
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));
    }

    @PutMapping(value = "/{appointment-id}/approve")
    public void approveAppointment(@PathVariable(value = "appointment-id") String appointmentId,
                                   @RequestParam(value = "appointment-time") String appointmentTime,
                                   @RequestParam(value = "appointment-duration") String appointmentDuration,
                                   HttpServletResponse response)
            throws IOException, AppointmentException {
        appointmentService.approveAppointment(Integer.valueOf(appointmentId), Long.valueOf(appointmentTime),
                                              Long.valueOf(appointmentDuration));
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));
    }
}
