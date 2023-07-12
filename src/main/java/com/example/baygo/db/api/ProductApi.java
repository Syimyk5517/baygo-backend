package com.example.baygo.db.api;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.responces.ColorResponse;
import com.example.baygo.db.dto.responces.SimpleResponse;
import com.example.baygo.db.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@Tag(name = "Product Admin API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductApi {
    private final ProductService productService;

    @Operation(summary = "Save the product", description = "This method saves the product")
    @PostMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse saveProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @Operation(summary = "Get colors", description = "This method gets the colors for saving products")
    @GetMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public List<ColorResponse> getColors(){
        return ColorResponse.getColors();
    }

    @Operation(summary = "Get barcode", description = "This method gets the barcode for saving products")
    @GetMapping("/barcode")
    @PreAuthorize("hasAuthority('SELLER')")
    public int getBarcode(){
        return productService.getBarcode();
    }
}
