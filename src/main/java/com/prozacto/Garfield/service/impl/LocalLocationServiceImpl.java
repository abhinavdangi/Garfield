package com.prozacto.Garfield.service.impl;

import com.prozacto.Garfield.service.LocationService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

@Service
@Qualifier("localService")
public class LocalLocationServiceImpl implements LocationService {

    private static final String BASE_PATH =
            FileSystems.getDefault().getPath("").toAbsolutePath().getParent().toString()
            + "/prozacto/patient/";
    private static final Logger LOG = LoggerFactory.getLogger(LocalLocationServiceImpl.class);

    private String getPatientPath(String patientId) {
        return BASE_PATH + patientId;
    }

    @Override
    public String getPatientPathLatest(String patientIdString) {
        return getPatientPath(patientIdString) + "/latest/patientRecord.zip";
    }

    @Override
    public String getPatientPathOld(String patientIdString) {
        return getPatientPath(patientIdString) + "/" + System.currentTimeMillis() + "/";
    }

    @Override
    public void copyData(String latestPath, String oldPath) throws IOException {
        if (latestPath == null) {
            return;
        }
        new File(latestPath).getParentFile().mkdirs();
        new File(oldPath).getParentFile().mkdirs();
        File source = new File(latestPath);
        File dest = new File(oldPath);
        if (source.exists()) {
            FileUtils.copyFileToDirectory(source, dest);
        }
    }

    @Override
    public void putData(String latestPath, File file) throws IOException {
        LOG.info(latestPath);
        LOG.info(String.valueOf(new File(latestPath).getParentFile().mkdirs()));
        FileUtils.copyFile(file, new File(latestPath));
    }

    @Override
    public void deleteData(String patientPathLatest) {
        FileUtils.deleteQuietly(new File(patientPathLatest));
    }

}
