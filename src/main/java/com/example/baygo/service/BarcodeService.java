package com.example.baygo.service;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public interface BarcodeService {
    byte[] generateBarcode(int barcodeValue);

    BufferedImage generateEAN13BarcodeImage(String barcode) throws Exception;
}
