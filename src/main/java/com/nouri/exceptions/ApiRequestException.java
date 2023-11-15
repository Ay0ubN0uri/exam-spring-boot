package com.nouri.exceptions;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {

    private HttpStatus httpStatus;

    public ApiRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}