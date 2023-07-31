package com.example.baygo.service;

import org.springframework.stereotype.Service;

@Service
public interface BarcodeService {
    byte[] generateBarcode(int barcodeValue);
}
