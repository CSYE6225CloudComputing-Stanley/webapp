package com.csye6225.webapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


@RestControllerAdvice
public class HttpExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(HttpExceptionHandler.class);

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpRequestOptionsAndHeadNotAllowed.class, HttpRequestPostPathVariableNotAllowed.class})
    public ResponseEntity<Void> handleMethodNotAllowed(Exception ex) {
        log.warn("Method not allowed: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpRequestPayloadNotAllowedException.class)
    public ResponseEntity<Void> handlePayloadNotAllowed(HttpRequestPayloadNotAllowedException ex) {
        log.warn("Payload not allowed: {}", ex.getStackTrace());
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpRequestPathVariableNotAllowed.class)
    public ResponseEntity<Void> handlePayloadNotAllowed(HttpRequestPathVariableNotAllowed ex) {
        log.warn("Path variables not allowed: {}", ex.getStackTrace());
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpRequestParameterNotAllowed.class)
    public ResponseEntity<Void> handlePayloadNotAllowed(HttpRequestParameterNotAllowed ex) {
        log.warn("Parameter not allowed: {}", ex.getStackTrace());
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpRequestMissingPathVariableException.class)
    public ResponseEntity<Void> handleMissingPathVariableException(HttpRequestMissingPathVariableException ex) {
        log.warn("missing path variables: {}", ex.getStackTrace());
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<String> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        log.warn("Missing request part: {}", ex.getStackTrace());
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        log.warn("media type not supported: {}", ex.getStackTrace());
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(MediaFileIsEmptyException.class)
    public ResponseEntity<String> handleMediaFileIsEmptyException(MediaFileIsEmptyException ex) {
        log.warn("media file is empty: {}", ex.getStackTrace());
        return ResponseEntity
                .badRequest()
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(ObjectIsNullException.class)
    public ResponseEntity<String> handleObjectIsNullException(ObjectIsNullException ex) {
        log.warn("object is null: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header("Cache-Control", "no-cache")
                .build();

    }
}
