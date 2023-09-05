package com.example.baygo.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public interface S3Service {
    Map<String, String> uploadFile(MultipartFile file) throws IOException;

    Map<String, String> deleteFile(String fileLink);

    String uploadImage(BufferedImage image, String fileName) throws IOException;
}
