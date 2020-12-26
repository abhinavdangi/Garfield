package com.prozacto.Garfield.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public interface LocationService {

    String getPatientPathLatest(String patientIdString);

    String getPatientPathOld(String patientIdString);

    void copyData(String latestPath, String oldPath) throws IOException;

    void putData(String latestPath, File file) throws IOException;

    void deleteData(String patientPathLatest);
}
