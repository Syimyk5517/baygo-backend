package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.ColorResponse;
import com.example.baygo.db.dto.response.PaginationResponseWithQuantity;
import com.example.baygo.db.dto.response.ProductBuyerResponse;
import com.example.baygo.db.dto.response.product.ProductGetByIdResponse;
import com.example.baygo.service.ProductService;
import com.example.baygo.service.SubProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/buyer/products")
@RequiredArgsConstructor
@Tag(name = "Product Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
@PermitAll
public class BuyerProductController {
    private final ProductService productService;
    private final SubProductService subProductService;

    @Operation(summary = "Get all products.", description = "This method gets all products with filters and search.")
    @GetMapping
    public PaginationResponseWithQuantity<ProductBuyerResponse> getAll(@RequestParam(required = false) String keyWord,
                                                                       @RequestParam(required = false) Long categoryId,
                                                                       @RequestParam(required = false) Long subCategoryId,
                                                                       @RequestParam(required = false) List<String> sizes,
                                                                       @RequestParam(required = false) List<String> compositions,
                                                                       @RequestParam(required = false) List<String> brands,
                                                                       @RequestParam(required = false) BigDecimal minPrice,
                                                                       @RequestParam(required = false) BigDecimal maxPrice,
                                                                       @RequestParam(required = false) List<String> colors,
                                                                       @RequestParam(required = false) String filterBy,
                                                                       @RequestParam(required = false) String sortBy,
                                                                       @RequestParam(required = false, defaultValue = "1") int page,
                                                                       @RequestParam(required = false, defaultValue = "16") int pageSize) {
        return productService.getAllProductsBuyer(keyWord, categoryId, subCategoryId, sizes, compositions, brands, minPrice, maxPrice, colors, filterBy, sortBy, page, pageSize);
    }

    @Operation(summary = "Find all similar products", description = "This method find all similar products with brand.")
    @GetMapping("/similar_product")
    List<ProductBuyerResponse> findAllSimilarProducts() {
        return null;
    }

    @Operation(summary = "Get colors", description = "This method gets the colors for buyer")
    @GetMapping("/colors")
    public List<ColorResponse> getColors() {
        return ColorResponse.getColors();
    }

    @GetMapping("/{subProductId}")
    public ProductGetByIdResponse getById(@PathVariable Long subProductId) {
        return subProductService.getById(subProductId);
    }
}
