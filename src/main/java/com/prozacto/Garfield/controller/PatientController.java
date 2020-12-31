package com.prozacto.Garfield.controller;

import com.prozacto.Garfield.domain.HttpResponse;
import com.prozacto.Garfield.exception.FileIOException;
import com.prozacto.Garfield.service.PatientService;
import com.prozacto.Garfield.utils.HttpResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping
    public void getPatientReport(@PathVariable("patient-id") String patientId,
                                 HttpServletResponse response)
            throws IOException, FileIOException {
        patientService.getPatientData(Integer.valueOf(patientId), response);
    }

    @PostMapping
    public void createPatientReport(@PathVariable(value = "patient-id") String patientId,
                                    @RequestParam(value = "file") MultipartFile zipFile,
                                    HttpServletResponse response)
            throws IOException, FileIOException {
        patientService.putPatientData(Integer.valueOf(patientId), zipFile, response);
        HttpResponseUtil
                .returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));

    }

    @PutMapping
    public void updatePatientReport(@PathVariable(value = "patient-id") String patientId,
                                    @RequestParam(value = "file") MultipartFile zipFile,
                                    HttpServletResponse response)
            throws IOException, FileIOException {
        patientService.putPatientData(Integer.valueOf(patientId), zipFile, response);
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));

    }

    @DeleteMapping
    public void revokePatientReport(@PathVariable(value = "patient-id") String patientId,
                                    HttpServletResponse response)
            throws IOException {
        patientService.deletePatientReport(Integer.valueOf(patientId));
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));
    }

}
