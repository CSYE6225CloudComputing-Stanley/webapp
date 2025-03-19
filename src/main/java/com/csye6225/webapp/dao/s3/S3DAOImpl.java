package com.csye6225.webapp.dao.s3;

import com.csye6225.webapp.entity.S3MetaData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class S3DAOImpl implements S3DAO {

    private final EntityManager entityManager;

    @Autowired
    public S3DAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(S3MetaData theS3MetaData) {
        entityManager.persist(theS3MetaData);
    }

    @Override
    public S3MetaData findObjectById(String id) {
        S3MetaData metadata = entityManager.find(S3MetaData.class, id);
        return metadata;
    }

    @Override
    public S3MetaData findObjectByFileName(String name) {
        try {
            return entityManager.createQuery(
                    "SELECT s FROM S3MetaData s WHERE s.fileName = :fileName", S3MetaData.class)
                    .setParameter("fileName", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteObject(S3MetaData object) {
        entityManager.remove(object);
    }
}
