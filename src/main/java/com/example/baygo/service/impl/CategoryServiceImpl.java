package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.CategoryResponse;
import com.example.baygo.repository.CategoryRepository;
import com.example.baygo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<CategoryResponse> getAllCategories() {
      return categoryRepository.getAllCategories();
    }
}
