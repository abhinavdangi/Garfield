package com.prozacto.Garfield.controller;

import com.prozacto.Garfield.domain.HttpResponse;
import com.prozacto.Garfield.domain.request.AppointmentRequest;
import com.prozacto.Garfield.exception.AppointmentException;
import com.prozacto.Garfield.exception.AuthenticationException;
import com.prozacto.Garfield.exception.ForbiddenException;
import com.prozacto.Garfield.exception.UserServiceException;
import com.prozacto.Garfield.service.AppointmentService;
import com.prozacto.Garfield.service.AuthenticationService;
import com.prozacto.Garfield.utils.HttpResponseUtil;
import com.prozacto.Garfield.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService,
                                 AuthenticationService authenticationService) {
        this.appointmentService = appointmentService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(value = "/{appointment-id}")
    public void getAppointment(@RequestHeader(value = "user-name") String userName,
                               @RequestHeader(value = "token") String token,
                               @PathVariable("appointment-id") String appointmentId,
                               HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException, IOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(userName)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        HttpResponseUtil.returnResponse(response, new HttpResponse(
                HttpStatus.OK,
                MapperUtil.getObjectMapper()
                        .writeValueAsString(appointmentService.getAppointment(appointmentId))));
    }

    @GetMapping
    public void getAppointmentsByDoctor(@RequestHeader(value = "user-name") String userName,
                                @RequestHeader(value = "token") String token,
                                @RequestParam(value = "doctor-email") String doctorEmail,
                                HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException, IOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(userName)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        HttpResponseUtil.returnResponse(response, new HttpResponse(
                HttpStatus.OK,
                MapperUtil.getObjectMapper()
                        .writeValueAsString(appointmentService.getAppointmentsByDoctor(doctorEmail))));
    }

    @PostMapping
    public void requestAppointment(@RequestHeader(value = "user-name") String userName,
                                   @RequestHeader(value = "token") String token,
                                   @RequestBody AppointmentRequest appointment,
                                   HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException,
            AppointmentException, IOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(userName)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        appointmentService.requestAppointment(appointment);
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));
    }

    @PutMapping(value = "/{appointment-id}")
    public void updateAppointment(@RequestHeader(value = "user-name") String userName,
                                  @RequestHeader(value = "token") String token,
                                  @PathVariable(value = "appointment-id") String appointmentId,
                                  @RequestParam(value = "status") String status,
                                  HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException, IOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(userName)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        appointmentService.updateAppointment(appointmentId, status);
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));
    }

    @PutMapping(value = "/{appointment-id}/approve")
    public void approveAppointment(@RequestHeader(value = "user-name") String userName,
                                   @RequestHeader(value = "token") String token,
                                   @PathVariable(value = "appointment-id") String appointmentId,
                                   @RequestParam(value = "appointment-time") String appointmentTime,
                                   @RequestParam(value = "appointment-duration") String appointmentDuration,
                                   HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException, IOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(userName)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        appointmentService.approveAppointment(appointmentId, appointmentTime, appointmentDuration);
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));
    }

    private boolean checkRole(String userName) {
        return true;
    }
}
