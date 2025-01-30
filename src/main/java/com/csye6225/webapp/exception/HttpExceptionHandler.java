package com.csye6225.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Void> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpPayloadNotSupportedException.class)
    public ResponseEntity<Void> handlePayloadNotAllowed(HttpPayloadNotSupportedException ex) {
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }
}
