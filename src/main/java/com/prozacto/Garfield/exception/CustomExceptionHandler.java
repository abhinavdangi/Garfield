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
    public void authenticationException(final HttpServletResponse response, final AuthenticationException e)
            throws IOException {
        returnResponse(response,
                       new HttpResponse(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(value = {UserServiceException.class})
    public void userServiceException(final HttpServletResponse response, final UserServiceException e)
            throws IOException {
        returnResponse(response,
                       new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }


}
