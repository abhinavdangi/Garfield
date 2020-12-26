package com.prozacto.Garfield.service;

import com.prozacto.Garfield.exception.FileIOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@Service
public interface PatientService {


    void getPatientData(Long patientId, HttpServletResponse response)
            throws IOException, FileIOException;

    void putPatientData(Long patientId, MultipartFile zipFile, HttpServletResponse response)
            throws IOException;

    void deletePatientReport(Long patientId) throws IOException;
}
