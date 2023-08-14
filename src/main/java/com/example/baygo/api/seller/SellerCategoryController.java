package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.CategoryResponse;
import com.example.baygo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seller/category")
@RequiredArgsConstructor
@Tag(name = "Seller category")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerCategoryController {
    private final CategoryService categoryService;
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping
    @Operation(summary = "Get all categories!",description = "This method gets all categories!!!")
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
