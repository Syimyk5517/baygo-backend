package com.example.baygo.service;

import com.example.baygo.db.dto.response.SubCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubCategoryService {
    List<SubCategoryResponse> getAllSubCategories(Long categoryId);
}
