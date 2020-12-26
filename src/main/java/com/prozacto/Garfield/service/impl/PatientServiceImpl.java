package com.prozacto.Garfield.service.impl;

import com.prozacto.Garfield.exception.FileIOException;
import com.prozacto.Garfield.service.LocationService;
import com.prozacto.Garfield.service.PatientService;
import com.prozacto.Garfield.utils.HttpResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@Service
public class PatientServiceImpl implements PatientService {

    private final LocationService locationService;

    @Autowired
    public PatientServiceImpl(@Qualifier("localService") LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void getPatientData(final Long patientId, HttpServletResponse response)
            throws IOException, FileIOException {
        String patientIdString = patientId.toString();
        String localPath = locationService.getPatientPathLatest(patientIdString);
        if ((new File(localPath)).exists()) {
            HttpResponseUtil.addFileToResponse("patientRecord_" + patientIdString + ".zip",
                                               "application/zip", localPath, response);
        } else {
            throw new FileIOException("No patient record present");
        }
    }

    @Override
    public void putPatientData(Long patientId, MultipartFile zipFile, HttpServletResponse response)
            throws IOException {
        String patientIdString = patientId.toString();
        locationService.copyData(locationService.getPatientPathLatest(patientIdString),
                                 locationService.getPatientPathOld(patientIdString));
        locationService.putData(locationService.getPatientPathLatest(patientIdString), zipFile);
    }

    @Override
    public void deletePatientReport(Long patientId) throws IOException {
        String patientIdString = patientId.toString();
        locationService.copyData(locationService.getPatientPathLatest(patientIdString),
                                 locationService.getPatientPathOld(patientIdString));
        locationService.deleteData(locationService.getPatientPathLatest(patientIdString));
    }

}
