package com.prozacto.Garfield.exception;

import static com.prozacto.Garfield.utils.HttpResponseUtil.returnResponse;

import com.prozacto.Garfield.domain.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles all the custom exceptions to be thrown out to the external world.
 */
@ControllerAdvice
public final class CustomExceptionHandler {

    @ExceptionHandler(value = {AuthenticationException.class})
    public void authenticationException(final HttpServletResponse response,
                                        final AuthenticationException e)
            throws IOException {
        returnResponse(response,
                       new HttpResponse(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(value = {UserServiceException.class})
    public void userServiceException(final HttpServletResponse response,
                                     final UserServiceException e)
            throws IOException {
        returnResponse(response,
                       new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public void forbiddenException(final HttpServletResponse response,
                                   final ForbiddenException e)
            throws IOException {
        returnResponse(response,
                       new HttpResponse(HttpStatus.FORBIDDEN, e.getMessage()));
    }

    @ExceptionHandler(value = {FileIOException.class})
    public void fileIOException(final HttpServletResponse response,
                                final FileIOException e)
            throws IOException {
        returnResponse(response,
                       new HttpResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(value = {AppointmentException.class})
    public void appointmentException(final HttpServletResponse response,
                                     final AppointmentException e)
            throws IOException {
        returnResponse(response,
                       new HttpResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    public void generalException(final HttpServletResponse response,
                                 final Exception e)
            throws IOException {
        returnResponse(response,
                       new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}
