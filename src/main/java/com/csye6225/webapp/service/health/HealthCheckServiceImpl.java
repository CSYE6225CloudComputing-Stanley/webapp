package com.csye6225.webapp.service.health;

import com.csye6225.webapp.dao.health.HealthCheckDAO;

import com.csye6225.webapp.entity.HealthCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    private final HealthCheckDAO healthCheckDAO;

    @Autowired
    public HealthCheckServiceImpl(HealthCheckDAO healthCheckDAO) {
        this.healthCheckDAO = healthCheckDAO;
    }

    @Override
    public HealthCheck initHealthCheck() {
        Instant now = Instant.now();
        HealthCheck healthCheck = new HealthCheck();
        healthCheck.setDatetime(now);
        return healthCheck;
    }

    @Override
    @Transactional
    public void save(HealthCheck healthCheck) {
        healthCheckDAO.save(healthCheck);
    }
}
