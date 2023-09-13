package com.example.baygo.api.seller;

import com.example.baygo.db.dto.request.SaveProductRequest;
import com.example.baygo.db.dto.request.UpdateProductDTO;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.service.ProductService;
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
@Tag(name = "Seller product")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerProductController {
    private final ProductService productService;

    @Operation(summary = "Save the product", description = "This method saves the product")
    @PostMapping
    public SimpleResponse saveProduct(@RequestBody @Valid SaveProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @Operation(summary = "Get all products", description = "This method gets all products of seller. The categoryId you can get on the SellerCategoryController. SortBy: dateOfChange, rating, quantity. Ascending: true, false")
    @GetMapping
    public PaginationResponse<ProductResponseForSeller> getAllProductForSeller(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyWord,
            @RequestParam(defaultValue = "dateOfChange") String sortBy,
            @RequestParam(defaultValue = "false") boolean ascending,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "11") int size) {
        return productService.findAll(categoryId, keyWord, sortBy, ascending, page, size);
    }

    @Operation(summary = "Delete product with product supplyId.", description = "This method delete product with product supplyId.")
    @DeleteMapping()
    public SimpleResponse deleteProduct(@RequestParam Long subProductId) {
        return productService.deleteProduct(subProductId);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by supplyId",
            description = "Retrieves detailed information about a product based on the provided ID.")
    public UpdateProductDTO getById(@PathVariable Long productId) {
        return productService.getById(productId);
    }

    @Operation(summary = "Update Product",
            description = "This method updates product information based on the provided data.")
    @PutMapping
    public SimpleResponse updateProduct(@RequestBody @Valid UpdateProductDTO request) {
        return productService.updateProduct(request);
    }
}