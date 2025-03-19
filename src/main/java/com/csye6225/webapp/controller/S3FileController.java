package com.csye6225.webapp.controller;


import com.csye6225.webapp.entity.S3MetaData;
import com.csye6225.webapp.exception.*;
import com.csye6225.webapp.service.s3.S3Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RequestMapping("/v1/file")
@RestController
public class S3FileController {

    private final S3Service S3Service;

    @Autowired
    public S3FileController(S3Service S3Service) {
        this.S3Service = S3Service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<S3MetaData> getFile(@PathVariable String id) {
        S3MetaData f = S3Service.getFile(id);
        if (f == null) {
            return ResponseEntity
                    .notFound()
                    .header("Cache-Control", "no-cache")
                    .build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(f);
    }

    @GetMapping()
    public ResponseEntity<Void> getFile() {
        throw new HttpRequestMissingPathVariableException();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path="/**")
    public ResponseEntity<S3MetaData> addFile(HttpServletRequest request, @RequestPart("profilePic") MultipartFile file) throws IOException {
        if (!request.getRequestURI().equals("/v1/file")) throw new HttpRequestPostPathVariableNotAllowed();

        if (file.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        if (request.getQueryString() != null) throw new HttpRequestParameterNotAllowed();

        String filename = file.getOriginalFilename();
        S3MetaData object = S3Service.getFileByFineName(filename);
        if (object != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        S3MetaData f = S3Service.addFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(f);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable String id) {
        S3MetaData object = S3Service.getFile(id);

        if (object == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        S3Service.deleteFile(object);

        return ResponseEntity
                .ok()
                .header("Cache-Control", "no-cache")
                .build();
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
