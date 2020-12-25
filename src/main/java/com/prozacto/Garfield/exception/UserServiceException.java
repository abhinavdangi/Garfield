package com.prozacto.Garfield.exception;

public class UserServiceException extends Exception{
    public UserServiceException(final String message) {
        super(message);
    }

    public UserServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
