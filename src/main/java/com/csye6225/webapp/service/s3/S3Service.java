package com.csye6225.webapp.service.s3;

import com.csye6225.webapp.entity.S3MetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    S3MetaData addFile(MultipartFile file) throws IOException;

    S3MetaData getFile(String id);

    S3MetaData getFileByFineName(String name);

    void deleteFile(S3MetaData object);

}
