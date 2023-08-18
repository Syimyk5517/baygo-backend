package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.SubCategoryResponse;
import com.example.baygo.repository.SubCategoryRepository;
import com.example.baygo.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public List<SubCategoryResponse> getAllSubCategories(Long categoryId) {
        return subCategoryRepository.getAllSubCategories(categoryId);
    }
}
