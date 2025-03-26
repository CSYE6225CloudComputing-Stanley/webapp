package com.csye6225.webapp.exception;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLSyntaxErrorException;

@RestControllerAdvice
public class DatabaseExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DatabaseExceptionHandler.class);

    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<Void> handleConnectException(JDBCConnectionException ex) {
        log.error("JDBC connection failed: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Cache-Control", "no-cache")
                .build();
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<Void> handleDataAccessException(SQLSyntaxErrorException ex) {
        log.error("Service is unavailable: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Cache-Control", "no-cache")
                .build();
    }
}
