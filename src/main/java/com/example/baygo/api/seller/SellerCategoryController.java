package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.CategoryResponse;
import com.example.baygo.db.dto.response.SubCategoryResponse;
import com.example.baygo.service.CategoryService;
import com.example.baygo.service.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/category")
@RequiredArgsConstructor
@Tag(name = "Seller category")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('SELLER')")
public class SellerCategoryController {
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @GetMapping
    @Operation(summary = "Get all categories!", description = "This method gets all categories!!!")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/sub-categories")
    @Operation(summary = "Get all sub categories!", description = "This method gets all subCategories!!!")
    public List<SubCategoryResponse> getAllSubCategories(@RequestParam(required = false) Long categoryId) {
        return subCategoryService.getAllSubCategories(categoryId);
    }
}
