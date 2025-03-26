package com.csye6225.webapp.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "s3_metadata")
public class S3MetaData {

    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "upload_date", nullable = false, updatable = false)
    private Instant uploadDate;

    public String getId() { return id; }
    public String getFileName() { return fileName; }
    public String getUrl() { return url; }
    public Instant getUploadDate() { return uploadDate; }

    public void setId(String id) { this.id = id; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setUrl(String url) { this.url = url; }
    public void setUploadDate(Instant uploadDate) { this.uploadDate = uploadDate; }

}
