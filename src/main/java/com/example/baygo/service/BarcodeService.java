package com.example.baygo.service;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
public interface BarcodeService {
    byte[] generateBarcode(int barcodeValue);

    List<BufferedImage> generateEAN13BarcodeImage(String barcode);

    List<String> generateProductBarcode(int quantity);
}
