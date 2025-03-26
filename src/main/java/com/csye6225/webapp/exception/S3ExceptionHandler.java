package com.csye6225.webapp.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@RestControllerAdvice
public class S3ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(S3ExceptionHandler.class);

    @ExceptionHandler(NoSuchBucketException.class)
    public ResponseEntity<Void> handleMethodNotAllowed(NoSuchBucketException ex) {
        log.error("S3 bucket is unavailable: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Cache-Control", "no-cache")
                .build();
    }
}
