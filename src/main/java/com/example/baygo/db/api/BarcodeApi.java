package com.example.baygo.db.api;

import com.example.baygo.db.service.BarcodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/barcode")
@RequiredArgsConstructor
@Tag(name = "Barcode API")
public class BarcodeApi {

    private final BarcodeService barcodeService;

    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping(value = "/barcode/{barcodeValue}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateBarcode(@PathVariable int barcodeValue){
        return barcodeService.generateBarcode(barcodeValue);
    }
}
