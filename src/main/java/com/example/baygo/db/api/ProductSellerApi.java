package com.example.baygo.db.api;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.response.ColorResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/products")
@RequiredArgsConstructor
@Tag(name = "Product Seller API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductSellerApi {
    private final ProductService productService;

    @Operation(summary = "Save the product", description = "This method saves the product")
    @PostMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse saveProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @Operation(summary = "Get colors", description = "This method gets the colors for saving products")
    @PreAuthorize("hasAuthority('SELLER')")
    public List<ColorResponse> getColors() {
        return ColorResponse.getColors();
    }

    @Operation(summary = "Get barcode", description = "This method gets the barcode for saving products")
    @PreAuthorize("hasAuthority('SELLER')")
    public int getBarcode() {
        return productService.getBarcode();
    }

    @Operation(summary = "Get all products", description = "This method gets all products of seller. Status: 'Все товары', 'В избранном', 'В корзине', 'Все акции'")
    @PreAuthorize("hasAuthority('SELLER')")
    public PaginationResponse<ProductResponseForSeller> getAllProductForSeller(
            @RequestParam(defaultValue = "Все товары") String status,
            @RequestParam(required = false) String keyWord,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "11") int size) {
        return productService.findAll(status, keyWord, page, size);
    }
}