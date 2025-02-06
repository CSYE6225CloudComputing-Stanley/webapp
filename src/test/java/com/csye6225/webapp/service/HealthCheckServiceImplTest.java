package com.csye6225.webapp.service;

import com.csye6225.webapp.dao.HealthCheckDAO;
import com.csye6225.webapp.entity.HealthCheck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class HealthCheckServiceImplTest {

    @Mock
    private HealthCheckDAO healthCheckDAO;

    @InjectMocks
    private HealthCheckServiceImpl healthCheckService;

    @Test
    public void testInitHealthCheck() {
        HealthCheck healthCheck = healthCheckService.initHealthCheck();
        assertNotNull(healthCheck);
        assertNotNull(healthCheck.getDatetime());
    }

    @Test
    public void testSave() {
        HealthCheck healthCheck = healthCheckService.initHealthCheck();
        healthCheckService.save(healthCheck);
        verify(healthCheckDAO, times(1)).save(healthCheck);
    }

}
