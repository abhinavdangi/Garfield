package com.prozacto.Garfield.controller;

import com.prozacto.Garfield.exception.AuthenticationException;
import com.prozacto.Garfield.exception.FileIOException;
import com.prozacto.Garfield.exception.ForbiddenException;
import com.prozacto.Garfield.exception.UserServiceException;
import com.prozacto.Garfield.service.AuthenticationService;
import com.prozacto.Garfield.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/patient/{patient-id}")
public class PatientController {

    private final PatientService patientService;
    private final AuthenticationService authenticationService;

    @Autowired
    public PatientController(PatientService patientService,
                             AuthenticationService authenticationService) {
        this.patientService = patientService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public void getPatientReport(@RequestHeader(value = "user-name") String userName,
                                 @RequestHeader(value = "token") String token,
                                 @PathVariable("patient-id") String patientId,
                                 HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException, IOException,
            FileIOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(patientId)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        patientService.getPatientData(Long.valueOf(patientId), response);
    }

    @PostMapping
    public void createPatientReport(@RequestHeader(value = "user-name") String userName,
                                    @RequestHeader(value = "token") String token,
                                    @PathVariable(value = "patient-id") String patientId,
                                    @RequestParam(value = "file") MultipartFile zipFile,
                                    HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException, IOException,
            FileIOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(patientId)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        patientService.putPatientData(Long.valueOf(patientId), zipFile, response);
    }

    @PutMapping
    public void updatePatientReport(@RequestHeader(value = "user-name") String userName,
                                    @RequestHeader(value = "token") String token,
                                    @PathVariable(value = "patient-id") String patientId,
                                    @RequestParam(value = "file") MultipartFile zipFile,
                                    HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException, IOException,
            FileIOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(patientId)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        patientService.putPatientData(Long.valueOf(patientId), zipFile, response);
    }

    @DeleteMapping
    public void revokePatientReport(@RequestHeader(value = "user-name") String userName,
                                    @RequestHeader(value = "token") String token,
                                    @PathVariable(value = "patient-id") String patientId,
                                    HttpServletResponse response)
            throws UserServiceException, AuthenticationException, ForbiddenException, IOException {
        authenticationService.checkToken(userName, token);
        if (!checkRole(patientId)) {
            throw new ForbiddenException("You are not authorized to perform this operation.");
        }
        patientService.deletePatientReport(Long.valueOf(patientId));
    }

    private boolean checkRole(String patientId) {
        return true;
    }
}
