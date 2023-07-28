package com.example.baygo.db.service.impl;

import com.example.baygo.db.service.BarcodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BarcodeServiceImpl implements BarcodeService {

    public byte[] generateBarcode(int barcodeValue){
        final int WIDTH = 400;
        final int HEIGHT = 100;

        // Create the barcode writer
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            // Encode the barcode value as a BitMatrix
            BitMatrix bitMatrix = writer.encode(String.valueOf(barcodeValue), BarcodeFormat.CODE_128, WIDTH, HEIGHT);

            // Convert the BitMatrix to a BufferedImage
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
                }
            }

            // Convert the BufferedImage to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}

