package com.prozacto.Garfield.exception;

public class AppointmentException extends Exception{
    public AppointmentException(final String message) {
        super(message);
    }

    public AppointmentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
