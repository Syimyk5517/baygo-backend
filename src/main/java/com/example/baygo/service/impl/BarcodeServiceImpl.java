package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.BarcodeWithImageResponse;
import com.example.baygo.service.BarcodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BarcodeServiceImpl implements BarcodeService {

    public BufferedImage getBarcodesWithImage(int quantity) {
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                160,
                BufferedImage.TYPE_BYTE_BINARY,
                false,
                0
        );

        for (String barcode : generateProductBarcode(quantity)) {
            EAN13Bean barcodeGenerator = new EAN13Bean();
            barcodeGenerator.generateBarcode(canvas, barcode);

        }
        return canvas.getBufferedImage();
    }

//    @Override
//    public BufferedImage generateEAN13BarcodeImage(String barcodeText) {
//        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
//                160,
//                BufferedImage.TYPE_BYTE_BINARY,
//                false,
//                0
//        );
//
//        for (String barcode : generateProductBarcode(2)) {
//            EAN13Bean barcodeGenerator = new EAN13Bean();
//            barcodeGenerator.getBarcodesWithImage(canvas, barcode);
//
//        }
//        return canvas.getBufferedImage();
//    }

//    @Override
//    public BufferedImage generateEAN13BarcodeImage(String barcodeText) {
//        int barcodeWidth = 160; // Ширина каждого штрихкода
//        int barcodeHeight = 100; // Высота каждого штрихкода
//
//        Set<String> barcodes = generateProductBarcode(4); // Генерация 5 штрихкодов (замените на нужное количество)
//
//        int totalWidth = barcodeWidth * barcodes.size() + 360; // Общая ширина (ширина каждого штрихкода * количество штрихкодов)
//        int totalHeight = barcodeHeight + 20; // Общая высота (высота каждого штрихкода)
//
//        // Создаем BufferedImage для объединения всех штрихкодов
//        BufferedImage combinedImage = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_BYTE_BINARY);
//        Graphics2D g2d = combinedImage.createGraphics();
//
//        int xPosition = 5; // Начальная позиция X для первого штрихкода
//        for (String barcode : barcodes) {
//            EAN13Bean barcodeGenerator = new EAN13Bean();
//            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
//                    barcodeWidth,
//                    BufferedImage.TYPE_BYTE_BINARY,
//                    false,
//                    0
//            );
//
//            barcodeGenerator.getBarcodesWithImage(canvas, barcode); // Рисуем текущий штрихкод
//            BufferedImage singleBarcodeImage = canvas.getBufferedImage();
//
//            // Рисуем текущий штрихкод на общем BufferedImage в соответствующей позиции
//            g2d.drawImage(singleBarcodeImage, xPosition, 0, null);
//
//            xPosition += barcodeWidth + 90; // Перемещаем позицию X для следующего штрихкода
//        }
//
//        g2d.dispose(); // Освобождаем ресурсы
//
//        return combinedImage;
//    }

    @Override
    public List<BufferedImage> generateEAN13BarcodeImage(String barcodeText) {
        List<BufferedImage> barcodeImages = new ArrayList<>();
        List<String> barcodeList = generateProductBarcode(1); // Генерация 24 штрихкода (4 страницы)

        int barcodeWidth = 160;
        int barcodeHeight = 100;
        int maxBarcodesPerRow = 2;
        int maxRowsPerPage = 4;
        int rowSpacing = 80; // Отступ между рядами
        int columnSpacing = 10; // Отступ между штрихкодами
        String barcodeTitle = "BayGo";

        for (int i = 0; i < barcodeList.size(); i += (maxBarcodesPerRow * maxRowsPerPage)) {
            List<String> batch = barcodeList.subList(i, Math.min(i + (maxBarcodesPerRow * maxRowsPerPage), barcodeList.size()));
            BufferedImage pageImage = new BufferedImage(595, 842, BufferedImage.TYPE_BYTE_BINARY); // A4 размер (595x842)
            Graphics2D g2d = pageImage.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, pageImage.getWidth(), pageImage.getHeight());

            int xPosition = columnSpacing;
            int yPosition = rowSpacing;
            int count = 0;

            for (String barcode : batch) {
                if (count % maxBarcodesPerRow == 0 && count != 0) {
                    xPosition = columnSpacing;
                    yPosition += barcodeHeight + rowSpacing;
                }
                EAN13Bean barcodeGenerator = new EAN13Bean();
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                        barcodeWidth,
                        BufferedImage.TYPE_BYTE_BINARY,
                        false,
                        0
                );

                barcodeGenerator.generateBarcode(canvas, barcode); // Рисуем текущий штрихкод
                BufferedImage singleBarcodeImage = canvas.getBufferedImage();
                xPosition += 40;
                g2d.drawImage(singleBarcodeImage, xPosition, yPosition, null);

                // Добавляем надпись наверху штрих-кода
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                int titleWidth = g2d.getFontMetrics().stringWidth(barcodeTitle);
                int titleX = xPosition + (barcodeWidth - titleWidth) / 2;
                int titleY = yPosition - 10; // Расстояние над надписью

                g2d.drawString(barcodeTitle, titleX, titleY);

                xPosition += barcodeWidth + columnSpacing;

                count++;
            }

            g2d.dispose();
            barcodeImages.add(pageImage);
        }

        return barcodeImages;
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
}

