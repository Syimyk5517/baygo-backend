package com.example.baygo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface S3Service {
    List<Map<String, String>> uploadFile(String bucketName, List<MultipartFile> files);

    Map<String, String> deleteFile(String fileLink);


}
