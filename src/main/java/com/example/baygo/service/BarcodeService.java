package com.example.baygo.service;

import com.example.baygo.db.dto.response.BarcodeWithImageResponse;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
public interface BarcodeService {
    List<BarcodeWithImageResponse> getBarcodesWithImage(int quantity);

    List<String> generateProductBarcode(int quantity);
}
