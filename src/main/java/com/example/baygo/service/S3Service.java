package com.example.baygo.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;
import java.util.Map;

public interface S3Service {
    List<Map<String, String>> uploadFile(String bucketName, List<MultipartFile> files);

    void putObject(PutObjectRequest request, RequestBody fromInputStream);

    Map<String, String> deleteFile(String fileLink);

    void deleteObject(Object baygo);
}
