package com.csye6225.webapp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@ConfigurationProperties(prefix = "aws")
public class S3Config {

    private String profile;
    private String region;
    private S3 s3;

    public static class S3 {
        private String bucket;
        public String getBucket() { return bucket; }
        public void setBucket(String bucket) { this.bucket = bucket; }
    }

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public S3 getS3() { return s3; }
    public void setS3(S3 s3) { this.s3 = s3; }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
//                .credentialsProvider(ProfileCredentialsProvider.create(profile))
                .credentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }
}