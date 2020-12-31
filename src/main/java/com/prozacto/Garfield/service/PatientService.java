package com.prozacto.Garfield.service;

import com.prozacto.Garfield.exception.FileIOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@Service
public interface PatientService {

    void getPatientData(Integer patientId, HttpServletResponse response)
            throws IOException, FileIOException;

    void putPatientData(Integer patientId, MultipartFile zipFile, HttpServletResponse response)
            throws IOException, FileIOException;

    void deletePatientReport(Integer patientId) throws IOException;

    Boolean checkPatientReport(Integer patientId);
}
