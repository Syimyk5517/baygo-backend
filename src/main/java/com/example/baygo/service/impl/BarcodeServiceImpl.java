package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.BarcodeWithImageResponse;
import com.example.baygo.db.dto.response.QRCodeWithImageResponse;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.service.BarcodeService;
import com.example.baygo.service.S3Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BarcodeServiceImpl implements BarcodeService {
    private final S3Service s3Service;

    public List<BarcodeWithImageResponse> getBarcodesWithImage(int quantity) {
//        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
//                160,
//                BufferedImage.TYPE_BYTE_BINARY,
//                false,
//                0
//        );

        List<BarcodeWithImageResponse> responses = new ArrayList<>();
        for (String barcode : generateProductBarcode(quantity)) {
            try {
//                EAN13Bean barcodeGenerator = new EAN13Bean();
//                barcodeGenerator.generateBarcode(canvas, barcode);
                EAN13Writer barcodeWriter = new EAN13Writer();
                BitMatrix bitMatrix = barcodeWriter.encode(barcode, BarcodeFormat.EAN_13, 500, 90);

                responses.add(new BarcodeWithImageResponse(barcode,
                        s3Service.uploadImage(MatrixToImageWriter.toBufferedImage(bitMatrix), barcode)));
            } catch (IOException e) {
                throw new BadRequestException("Get barcodes with images impl error");
            }
        }
        return responses;
    }

    @Override
    public List<String> generateProductBarcode(int quantity) {
        List<String> barcodes = new LinkedList<>();
        while (quantity > 0) {
            StringBuilder barcode = new StringBuilder("470");
            UUID uuid = UUID.randomUUID();
            barcode.append(uuid.toString().replaceAll("[^0-9]", ""), 0, 9);

            int sum = 0;
            for (int i = 0; i < 12; i++) {
                int digit = Character.getNumericValue(barcode.charAt(i));
                sum += (i % 2 == 0) ? digit : digit * 3;
            }
            int checksum = (10 - (sum % 10)) % 10;
            barcode.append(checksum);
            barcodes.add(barcode.toString());
            quantity--;
        }
        return barcodes;
    }

    @Override
    public QRCodeWithImageResponse generateQrCode() {
        UUID uuid = UUID.randomUUID();
        String qrCodeText = uuid.toString().replaceAll("[^0-9]", "").substring(1, 9);
        QRCodeWriter barcodeWriter = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = barcodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 200, 200);

        return QRCodeWithImageResponse.builder()
                .qrCode(qrCodeText)
                .qrCodeImage(s3Service.uploadImage(MatrixToImageWriter.toBufferedImage(bitMatrix), qrCodeText))
                .build();

        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}