package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.BarcodeWithImageResponse;
import com.example.baygo.service.BarcodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/barcode")
@RequiredArgsConstructor
@Tag(name = "Seller barcode")
@PreAuthorize("hasAuthority('SELLER')")
public class SellerBarcodeController {

    private final BarcodeService barcodeService;

    @GetMapping
    public BufferedImage generateBarcode(@RequestParam int quantity) {
        return barcodeService.getBarcodesWithImage(quantity);
    }

    @GetMapping(value = "/ean13/{barcode}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> barbecueEAN13BarcodePDF(@PathVariable("barcode") String barcode) throws IOException {
        List<String> barcodeList = generateProductBarcode(8); // Замените на нужное количество
        List<BufferedImage> barcodeImages = barcodeService.generateEAN13BarcodeImage("df");

        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            for (BufferedImage image : barcodeImages) {
                PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
                    contentStream.drawImage(pdImage, 0, 0);
                }
            }

            document.save(pdfStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("barcodes.pdf")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfStream.size())
                .body(pdfStream.toByteArray());
    }

    @GetMapping("/numbers")
    public List<String> generateProductBarcode(@RequestParam int quantity) {
        return barcodeService.generateProductBarcode(quantity);
    }
}
