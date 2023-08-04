package com.example.baygo.service.impl;

import com.example.baygo.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3;
    @Value("${aws_s3_link}")
    private String BUCKET_PATH;

    @Override
    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket("baygo")
                .contentType("jpeg")
                .contentType("png")
                .contentType("ogg")
                .contentType("mp3")
                .contentType("mpeg")
                .contentType("ogg")
                .contentType("m4a")
                .contentType("oga")
                .contentLength(file.getSize())
                .key(key)
                .build();
        s3.putObject(put, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return Map.of(
                "link", BUCKET_PATH + key);
    }

    @Override
    public Map<String, String> deleteFile(String fileLink) {
        try {
            String key = fileLink.substring(BUCKET_PATH.length());
            s3.deleteObject(dor -> dor.bucket("baygo").key(key).build());
        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
        return Map.of(
                "message", fileLink + " has been deleted");
    }
}