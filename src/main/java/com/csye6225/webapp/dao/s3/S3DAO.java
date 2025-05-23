package com.csye6225.webapp.dao.s3;

import com.csye6225.webapp.entity.S3MetaData;

public interface S3DAO {
    void save(S3MetaData theS3MetaData);

    S3MetaData findObjectById(String id);

    void deleteObjectById(String id);
}
