package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.BarcodeWithImageResponse;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.service.BarcodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
    private final S3Client s3;
    @Value("${aws_bucket_name}")
    private String BUCKET_NAME;
    @Value("${aws_s3_link}")
    private String BUCKET_PATH;

    public List<BarcodeWithImageResponse> getBarcodesWithImage(String barcode) {
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                160,
                BufferedImage.TYPE_BYTE_BINARY,
                false,
                0
        );

        List<BarcodeWithImageResponse> responses = new ArrayList<>();
//        for (String barcode : generateProductBarcode(quantity)) {
            try {
                EAN13Bean barcodeGenerator = new EAN13Bean();
                barcodeGenerator.generateBarcode(canvas, barcode);

                responses.add(new BarcodeWithImageResponse(barcode,
                        uploadImageToS3(canvas.getBufferedImage(), barcode)));
            } catch (IOException e) {
                throw new BadRequestException("Get barcodes with images impl error");
            }
//        }
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

    private String uploadImageToS3(BufferedImage image, String fileName) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        byte[] imageBytes = os.toByteArray();

        String key = fileName + "barcode.png";

        long contentLength = imageBytes.length;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .contentType("image/png")
                .contentLength(contentLength)
                .build();

        s3.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));
        return BUCKET_PATH + key;
    }
}