package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.BuyerCategoryResponse;
import com.example.baygo.db.dto.response.CategoryResponse;
import com.example.baygo.repository.CategoryRepository;
import com.example.baygo.repository.SubCategoryRepository;
import com.example.baygo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    @Override
    public List<CategoryResponse> getAllCategories() {
      return categoryRepository.getAllCategories();
    }

    @Override
    public List<BuyerCategoryResponse> getAllCategoriesWithSubCategories() {
        List<CategoryResponse> categories = categoryRepository.getAllCategories();
        List<BuyerCategoryResponse> categoryResponses = new ArrayList<>();
        for (CategoryResponse category : categories) {
            BuyerCategoryResponse rp = new BuyerCategoryResponse();
            rp.setCategoryId(category.categoryId());
            rp.setCategoryName(category.categoryName());
            rp.setSubcategories(subCategoryRepository.getAllSubCategories(category.categoryId()));
            categoryResponses.add(rp);
        }
        return categoryResponses;
    }
}
