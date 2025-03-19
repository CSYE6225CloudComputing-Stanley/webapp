package com.csye6225.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpRequestOptionsAndHeadNotAllowed.class, HttpRequestPostPathVariableNotAllowed.class})
    public ResponseEntity<Void> handleMethodNotAllowed(Exception ex) {
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

    @ExceptionHandler(HttpRequestMissingPathVariableException.class)
    public ResponseEntity<Void> handleMissingPathVariableException(HttpRequestMissingPathVariableException ex) {
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<String> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }
}
