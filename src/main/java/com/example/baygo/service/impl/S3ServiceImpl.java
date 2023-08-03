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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3;
    @Value("${aws_s3_link}")
    private String BUCKET_PATH;


    @Override
    public List<Map<String, String>> uploadFile(String bucketName, List<MultipartFile> files) {
        List<Map<String, String>> uploadResults = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String key = System.currentTimeMillis() + file.getOriginalFilename();
                PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).contentType(file.getContentType()).contentLength(file.getSize()).key(key).build();
                s3.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

                String fileLink = BUCKET_PATH + key;
                Map<String, String> uploadResult = new HashMap<>();
                uploadResult.put("filename", file.getOriginalFilename());
                uploadResult.put("link", fileLink);
                uploadResults.add(uploadResult);
            } catch (Exception e) {
                e.printStackTrace();
                Map<String, String> uploadResult = new HashMap<>();
                uploadResult.put("filename", file.getOriginalFilename());
                uploadResult.put("error", "Failed to upload file");
                uploadResults.add(uploadResult);
            }
        }

        return uploadResults;

    }

    @Override
    public void putObject(PutObjectRequest request, RequestBody fromInputStream) {
        s3.putObject(request, fromInputStream);
    }


    public Map<String, String> deleteFile(String fileLink) {
        try {
            String key = fileLink.substring(BUCKET_PATH.length());
            s3.deleteObject((DeleteObjectRequest) buildDeleteObjectRequest(key));

            return Map.of("message", fileLink + " has been deleted");
        } catch (S3Exception e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to delete file: " + e.getMessage());
        }
    }

    private Object buildDeleteObjectRequest(String key) {
        return DeleteObjectRequest.builder().bucket("baygo").key(key).build();
    }
    @Override
    public void deleteObject(Object baygo) {
        s3.deleteObject((DeleteObjectRequest) baygo);
    }

}
