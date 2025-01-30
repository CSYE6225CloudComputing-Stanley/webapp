package com.csye6225.webapp.dao;

import com.csye6225.webapp.entity.HealthCheck;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HealthCheckDAOImpl implements HealthCheckDAO {

    private final EntityManager entityManager;

    @Autowired
    public HealthCheckDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(HealthCheck theHealthCheck) {
        entityManager.persist(theHealthCheck);
    }
}
