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

    @ExceptionHandler(HttpRequestPayloadNotAllowedException.class)
    public ResponseEntity<Void> handlePayloadNotAllowed(HttpRequestPayloadNotAllowedException ex) {
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpRequestPathVariableNotAllowed.class)
    public ResponseEntity<Void> handlePayloadNotAllowed(HttpRequestPathVariableNotAllowed ex) {
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpRequestParameterNotAllowed.class)
    public ResponseEntity<Void> handlePayloadNotAllowed(HttpRequestParameterNotAllowed ex) {
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }
}
