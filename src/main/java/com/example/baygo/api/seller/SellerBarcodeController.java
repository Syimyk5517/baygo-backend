package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.BarcodeWithImageResponse;
import com.example.baygo.service.BarcodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barcodes")
@RequiredArgsConstructor
@Tag(name = "Seller barcode")
@PreAuthorize("hasAuthority('SELLER')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerBarcodeController {

    private final BarcodeService barcodeService;

    @Operation(summary = "Generate barcodes for sizes", description = "This method to generate barcodes for saving sizes")
    @GetMapping("/generate")
    public List<String> generateProductBarcode(@RequestParam int quantity) {
        return barcodeService.generateProductBarcode(quantity);
    }

    @Operation(summary = "Generate barcodes with image", description = "This method to generate barcodes with image for supply box, for access card and ...")
    @GetMapping("/generate-image-barcode")
    public List<BarcodeWithImageResponse> generateBarcodeWithImage(@RequestParam String quantity) {
        return barcodeService.getBarcodesWithImage(quantity);
    }
}
