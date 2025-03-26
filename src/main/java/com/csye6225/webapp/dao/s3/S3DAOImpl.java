package com.csye6225.webapp.dao.s3;

import com.csye6225.webapp.entity.S3MetaData;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;


@Repository
public class S3DAOImpl implements S3DAO {

    private final EntityManager entityManager;

    private final MeterRegistry meterRegistry;

    @Autowired
    public S3DAOImpl(EntityManager entityManager, MeterRegistry meterRegistry) {
        this.entityManager = entityManager;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void save(S3MetaData theS3MetaData) {
        long start = System.currentTimeMillis();
        try {
            entityManager.persist(theS3MetaData);
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.post.v1.file.mysql.timer").record(Duration.ofMillis(duration));
        }
    }

    @Override
    public S3MetaData findObjectById(String id) {
        long start = System.currentTimeMillis();
        try {
            S3MetaData metadata = entityManager.find(S3MetaData.class, id);
            return metadata;
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.get.v1.file.id.mysql.timer").record(Duration.ofMillis(duration));
        }

    }

    @Override
    public void deleteObjectById(String id) {
        long start = System.currentTimeMillis();
        try {
            entityManager.createQuery("DELETE FROM S3MetaData s WHERE s.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.delete.v1.file.id.mysql.timer").record(Duration.ofMillis(duration));
        }
    }
}
