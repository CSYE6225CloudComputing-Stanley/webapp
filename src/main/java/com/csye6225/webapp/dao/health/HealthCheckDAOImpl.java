package com.csye6225.webapp.dao.health;

import com.csye6225.webapp.entity.HealthCheck;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class HealthCheckDAOImpl implements HealthCheckDAO {

    private final EntityManager entityManager;

    private final MeterRegistry meterRegistry;

    @Autowired
    public HealthCheckDAOImpl(EntityManager entityManager, MeterRegistry meterRegistry) {
        this.entityManager = entityManager;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void save(HealthCheck theHealthCheck) {
        long start = System.currentTimeMillis();
        try {
            entityManager.persist(theHealthCheck);
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.get.healthz.mysql.timer").record(Duration.ofMillis(duration));
        }
    }
}
