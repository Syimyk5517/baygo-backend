package com.example.baygo.service;

import com.example.baygo.db.dto.response.BarcodeWithImageResponse;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
public interface BarcodeService {
    BufferedImage getBarcodesWithImage(int quantity);

    List<BufferedImage> generateEAN13BarcodeImage(String barcode);

    List<String> generateProductBarcode(int quantity);
}
