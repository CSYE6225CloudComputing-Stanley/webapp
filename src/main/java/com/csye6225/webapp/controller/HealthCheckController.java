package com.csye6225.webapp.controller;


import com.csye6225.webapp.exception.*;
import com.csye6225.webapp.entity.HealthCheck;
import com.csye6225.webapp.service.HealthCheckService;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
public class HealthCheckController {

    private final HealthCheckService healthCheckService;

    private final MeterRegistry meterRegistry;

    private static final Logger log = LoggerFactory.getLogger(HealthCheckController.class);

    @Autowired
    public HealthCheckController(HealthCheckService healthCheckService, MeterRegistry meterRegistry) {
        this.healthCheckService = healthCheckService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/healthz/**")
    public ResponseEntity<Void> getHealthCheckStatus(HttpServletRequest request) {
        log.info("Received GET /healthz");
        meterRegistry.counter("api.get.healthz.count").increment();
        long start = System.currentTimeMillis();

        try {
            if (!request.getRequestURI().equals("/healthz")) throw new HttpRequestPathVariableNotAllowed();

            if (request.getContentLength() > 0) throw new HttpRequestPayloadNotAllowedException();

            if (request.getQueryString() != null) throw new HttpRequestParameterNotAllowed();

            HealthCheck healthCheck = healthCheckService.initHealthCheck();
            healthCheckService.save(healthCheck);

            return ResponseEntity
                    .ok()
                    .header("Cache-Control", "no-cache")
                    .build();
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.get.healthz.timer").record(Duration.ofMillis(duration));
        }
    }

    @RequestMapping(value = "/healthz", method = {RequestMethod.OPTIONS, RequestMethod.HEAD})
    public void handleOptionsAndHeadRequest() {
        throw new HttpRequestOptionsAndHeadNotAllowed();
    }
}