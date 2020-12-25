package com.prozacto.Garfield.domain;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class contains the attributes for HttpResponse.
 */
@Getter
@Setter
@Data
@ToString
public class HttpResponse {

    private HttpStatus httpStatus;
    private String message;

    public HttpResponse(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
