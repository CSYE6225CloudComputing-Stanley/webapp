package com.csye6225.webapp.controller;


import com.csye6225.webapp.entity.S3MetaData;
import com.csye6225.webapp.exception.*;
import com.csye6225.webapp.service.s3.S3Service;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.Duration;


@RequestMapping("/v2/file")
@RestController
public class S3FileController {

    private final S3Service S3Service;

    private final MeterRegistry meterRegistry;

    private static final Logger log = LoggerFactory.getLogger(S3FileController.class);

    @Autowired
    public S3FileController(S3Service S3Service, MeterRegistry meterRegistry) {
        this.S3Service = S3Service;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/{id}")
    public ResponseEntity<S3MetaData> getFile(@PathVariable String id) {
        log.info("Received GET S3 /v1/file/{id}");
        meterRegistry.counter("api.get.v1.file.id.count").increment();
        long start = System.currentTimeMillis();

        try {
            S3MetaData f = S3Service.getFile(id);
            if (f == null) {
                throw new ObjectIsNullException();
            }
            return ResponseEntity.status(HttpStatus.OK).body(f);
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.get.v1.file.id.timer").record(Duration.ofMillis(duration));
        }
    }

    @GetMapping()
    public ResponseEntity<Void> getFile() {
        throw new HttpRequestMissingPathVariableException();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path="/**")
    public ResponseEntity<S3MetaData> addFile(HttpServletRequest request, @RequestPart("profilePic") MultipartFile file) throws IOException {
        log.info("Received S3 POST /v1/file");
        meterRegistry.counter("api.post.v1.file.count").increment();
        long start = System.currentTimeMillis();

        try {
            if (!request.getRequestURI().equals("/v1/file")) throw new HttpRequestPostPathVariableNotAllowed();

            if (file.isEmpty()) { throw new MediaFileIsEmptyException(); }

            if (request.getQueryString() != null) throw new HttpRequestParameterNotAllowed();

            S3MetaData f = S3Service.addFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(f);
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.post.v1.file.timer").record(Duration.ofMillis(duration));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable String id) {
        log.info("Received s3 DELETE /v1/file/{id}");
        meterRegistry.counter("api.delete.v1.file.id.count").increment();
        long start = System.currentTimeMillis();

        try {
            S3MetaData object = S3Service.getFile(id);

            if (object == null) { throw new ObjectIsNullException(); }

            S3Service.deleteFile(object);

            return ResponseEntity
                    .ok()
                    .header("Cache-Control", "no-cache")
                    .build();
        } finally {
            long duration = System.currentTimeMillis() - start;
            meterRegistry.timer("api.delete.v1.file.id.timer").record(Duration.ofMillis(duration));
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteFile() {
        throw new HttpRequestMissingPathVariableException();
    }

    @RequestMapping(value = "/**", method = {RequestMethod.OPTIONS, RequestMethod.HEAD})
    public void handleOptionsAndHeadRequest() {
        throw new HttpRequestOptionsAndHeadNotAllowed();
    }

    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.HEAD})
    public void handleOptionsAndHeadRequestWithNoPathvariable() {
        throw new HttpRequestOptionsAndHeadNotAllowed();
    }
}
