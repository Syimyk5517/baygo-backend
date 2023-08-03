package com.example.baygo.service.impl;

import com.example.baygo.service.S3Service;
import com.google.api.client.util.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3;
    @Value("${aws_s3_link}")
    private String BUCKET_PATH;


    @Override
    public List<Map<String, String>> uploadFile(String bucketName, List<MultipartFile> files) {
        return files.stream().map(file -> {
            try {
                String key = Instant.now().toEpochMilli() + file.getOriginalFilename();
                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .contentType(file.getContentType())
                        .contentLength(file.getSize())
                        .key(key)
                        .build();
                s3.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

                String fileLink = BUCKET_PATH + key;
                Map<String, String> uploadResult = new HashMap<>();
                uploadResult.put("filename", file.getOriginalFilename());
                uploadResult.put("link", fileLink);
                return uploadResult;
            } catch (Exception e) {
                e.printStackTrace();
                Map<String, String> uploadResult = new HashMap<>();
                uploadResult.put("filename", file.getOriginalFilename());
                uploadResult.put("error", "Failed to upload file");
                return uploadResult;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> deleteFile(String fileLink) {
        try {
            String key = fileLink.substring(BUCKET_PATH.length());
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket("baygo")
                    .key(key)
                    .build();
            s3.deleteObject(request);

            return Collections.singletonMap("message", fileLink + " has been deleted");
        } catch (S3Exception e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to delete file: " + e.getMessage());
        }
    }
}