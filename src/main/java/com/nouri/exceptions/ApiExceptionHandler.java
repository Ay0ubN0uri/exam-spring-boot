package com.nouri.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = { ApiRequestException.class })
    public ResponseEntity<Object> handleException(ApiRequestException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getHttpStatus());
        return new ResponseEntity<>(errorDetails, ex.getHttpStatus());
    }
}