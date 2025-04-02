package com.csye6225.webapp.controller;


import com.csye6225.webapp.entity.HealthCheck;
import com.csye6225.webapp.exception.HttpRequestParameterNotAllowed;
import com.csye6225.webapp.exception.HttpRequestPathVariableNotAllowed;
import com.csye6225.webapp.exception.HttpRequestPayloadNotAllowedException;
import com.csye6225.webapp.service.health.HealthCheckService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.JDBCConnectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Instant;


@ExtendWith(MockitoExtension.class)
public class HealthCheckControllerTest {

    @Mock
    private HealthCheckService healthCheckService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private HealthCheckController healthCheckController;

    private MeterRegistry meterRegistry;

    @BeforeEach
    public void setup() {
        meterRegistry = new SimpleMeterRegistry();
        healthCheckController = new HealthCheckController(healthCheckService, meterRegistry);
    }

    @Test
    public void testSuccess() {
        when(request.getRequestURI()).thenReturn("/healthz");
        when(request.getContentLength()).thenReturn(0);
        when(request.getQueryString()).thenReturn(null);
        HealthCheck healthCheck = new HealthCheck();
        Instant now = Instant.now();
        healthCheck.setDatetime(now);
        when(healthCheckService.initHealthCheck()).thenReturn(healthCheck);

        ResponseEntity<Void> response = healthCheckController.getHealthCheckStatus(request);
        verify(healthCheckService, times(1)).initHealthCheck();
        verify(healthCheckService, times(1)).save(healthCheck);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getHeaders().containsKey("Cache-Control"));
        assertEquals("no-cache", response.getHeaders().getFirst("Cache-Control"));
    }

    @Test
    public void testHttpRequestPayloadNotAllowedException() {
        when(request.getRequestURI()).thenReturn("/healthz");
        when(request.getContentLength()).thenReturn(10);
        assertThrows(HttpRequestPayloadNotAllowedException.class, () -> {
            healthCheckController.getHealthCheckStatus(request);
        });
    }


    @Test
    public void testHttpRequestParameterNotAllowed() {
        when(request.getRequestURI()).thenReturn("/healthz");
        when(request.getContentLength()).thenReturn(0);
        when(request.getQueryString()).thenReturn("rrr=rrr");
        assertThrows(HttpRequestParameterNotAllowed.class, () -> {
            healthCheckController.getHealthCheckStatus(request);
        });
    }

    @Test
    public void testHttpRequestPathVariableNotAllowed() {
        when(request.getRequestURI()).thenReturn("/healthz/test");
        assertThrows(HttpRequestPathVariableNotAllowed.class, () -> {
            healthCheckController.getHealthCheckStatus(request);
        });
    }

    @Test
    public void testJDBCConnectionException() {
        HealthCheck healthCheck = new HealthCheck();
        Instant now = Instant.now();
        healthCheck.setDatetime(now);

        doThrow(new JDBCConnectionException("", null))
                .when(healthCheckService)
                .save(any(HealthCheck.class));

        assertThrows(JDBCConnectionException.class, () -> {
            healthCheckService.save(healthCheck);
        });
    }
}
