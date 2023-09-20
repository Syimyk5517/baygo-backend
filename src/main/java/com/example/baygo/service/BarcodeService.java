package com.example.baygo.service;

import com.example.baygo.db.dto.response.BarcodeWithImageResponse;
import com.example.baygo.db.dto.response.QRCodeWithImageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BarcodeService {
    List<BarcodeWithImageResponse> getBarcodesWithImage(int quantity);

    List<String> generateProductBarcode(int quantity);

    QRCodeWithImageResponse generateQrCode();
}
