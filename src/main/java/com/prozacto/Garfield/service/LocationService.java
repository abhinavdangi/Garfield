package com.prozacto.Garfield.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface LocationService {

    String getPatientPathLatest(String patientIdString);

    String getPatientPathOld(String patientIdString);

    void copyData(String latestPath, String oldPath) throws IOException;

    void putData(String latestPath, MultipartFile multipartFile) throws IOException;

    void deleteData(String patientPathLatest);
}
