package com.prozacto.Garfield.exception;

public class ForbiddenException extends Exception {
    public ForbiddenException(final String message) {
        super(message);
    }

    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
