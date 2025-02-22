package com.csye6225.webapp.controller;


import com.csye6225.webapp.exception.HttpRequestParameterNotAllowed;
import com.csye6225.webapp.exception.HttpRequestPayloadNotAllowedException;
import com.csye6225.webapp.entity.HealthCheck;
import com.csye6225.webapp.exception.HttpRequestPathVariableNotAllowed;
import com.csye6225.webapp.service.HealthCheckService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private final HealthCheckService healthCheckService;

    @Autowired
    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/healthz/**")
    public ResponseEntity<Void> getHealthCheckStatus(HttpServletRequest request) {
        if (!request.getRequestURI().equals("/healthz")) throw new HttpRequestPathVariableNotAllowed();
        

        if (request.getContentLength() > 0) throw new HttpRequestPayloadNotAllowedException();

        if (request.getQueryString() != null) throw new HttpRequestParameterNotAllowed();

        HealthCheck healthCheck = healthCheckService.initHealthCheck();
        healthCheckService.save(healthCheck);

        return ResponseEntity
                .ok()
                .header("Cache-Control", "no-cache")
                .build();
    }
}