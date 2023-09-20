package com.example.baygo.service;

import com.example.baygo.db.dto.response.BuyerCategoryResponse;
import com.example.baygo.db.dto.response.CategoryResponse;
import com.example.baygo.db.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    List<BuyerCategoryResponse> getAllCategoriesWithSubCategories();
}
