package com.csye6225.webapp.service;

import com.csye6225.webapp.entity.HealthCheck;

public interface HealthCheckService {

    HealthCheck initHealthCheck();

    void save(HealthCheck healthCheck);
}
