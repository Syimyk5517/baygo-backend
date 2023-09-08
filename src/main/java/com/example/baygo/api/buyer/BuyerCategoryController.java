package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.BuyerCategoryResponse;
import com.example.baygo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get categories", description = "This method gets all categories with subcategories!")
    @PermitAll
    public List<BuyerCategoryResponse> getAllCategories() {
        return categoryService.getAllCategoriesWithSubCategories();
    }
}
