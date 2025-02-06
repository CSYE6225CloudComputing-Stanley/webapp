package com.csye6225.webapp.dao;

import com.csye6225.webapp.entity.HealthCheck;
import jakarta.persistence.EntityManager;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;



@ExtendWith(MockitoExtension.class)
class HealthCheckDAOImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private HealthCheckDAOImpl healthCheckDAO;


    @Test
    public void testSave() {
        HealthCheck healthCheck = new HealthCheck();
        Instant now = Instant.now();
        healthCheck.setDatetime(now);
        healthCheckDAO.save(healthCheck);
        verify(entityManager, times(1)).persist(healthCheck);
    }
}

