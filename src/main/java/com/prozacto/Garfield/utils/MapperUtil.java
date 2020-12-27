package com.prozacto.Garfield.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil {

    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
