package com.nouri.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus httpStatus;

    public ErrorDetails(LocalDateTime timestamp, String message, HttpStatus httpStatus) {
        this.timestamp = timestamp;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}