package com.example.baygo.api.seller;

import com.example.baygo.db.dto.response.SubCategoryResponse;
import com.example.baygo.service.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/subCategory")
@RequiredArgsConstructor
@Tag(name = "Seller subCategory")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SellerSubCategoryController {
    private final SubCategoryService subCategoryService;
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/{categoryId}")
    @Operation(summary = "Get all subCategories!",description = "This method gets all subCategories!!!")
    public List<SubCategoryResponse> getAllSubCategories(@PathVariable Long categoryId) {
        return subCategoryService.getAllSubCategories(categoryId);
    }
}
