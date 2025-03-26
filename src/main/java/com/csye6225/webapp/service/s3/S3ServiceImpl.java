package com.csye6225.webapp.service.s3;

import com.csye6225.webapp.configuration.S3Config;
import com.csye6225.webapp.dao.s3.S3DAO;
import com.csye6225.webapp.entity.S3MetaData;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;


@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final String bucketName;
    private final S3DAO s3DAO;
    private final MeterRegistry meterRegistry;


    @Autowired
    public S3ServiceImpl(S3Client s3Client, S3Config s3Config, S3DAO s3DAO, MeterRegistry meterRegistry) {
        this.s3Client = s3Client;
        this.bucketName = s3Config.getS3().getBucket();
        this.s3DAO = s3DAO;
        this.meterRegistry = meterRegistry;
    }

    @Transactional
    @Override
    public S3MetaData addFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        long start = System.currentTimeMillis();
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uuid)
                    .contentType(file.getContentType())
                    .build();
            try (InputStream inputStream = file.getInputStream()) {
                RequestBody body = RequestBody.fromInputStream(inputStream, file.getSize());
                s3Client.putObject(request, body);
            }
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.post.v1.file.s3.timer").record(Duration.ofMillis(duration));
        }

        URL fileUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(uuid));
        String formattedUrl = fileUrl.toString().replace("https://", "");
        S3MetaData s3MetaData = new S3MetaData();
        s3MetaData.setId(uuid);
        s3MetaData.setFileName(filename);
        s3MetaData.setUploadDate(Instant.now());
        s3MetaData.setUrl(formattedUrl);

        s3DAO.save(s3MetaData);

        return s3MetaData;
    }

    @Override
    public S3MetaData getFile(String id) {
        return s3DAO.findObjectById(id);
    }

    @Override
    @Transactional
    public void deleteFile(S3MetaData object) {
        String url = object.getUrl();
        String bucketName = extractBucketName(url);
        String key = object.getId();

        long start = System.currentTimeMillis();
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.deleteObject(request);
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.delete.v1.file.s3.timer").record(Duration.ofMillis(duration));
        }

        s3DAO.deleteObjectById(key);
    }

    private String extractBucketName(String s3Url) {
        return s3Url.split("\\.")[0];
    }
}