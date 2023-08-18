package com.example.baygo.repository;

import com.example.baygo.db.dto.response.SubCategoryResponse;
import com.example.baygo.db.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query("""
            SELECT NEW com.example.baygo.db.dto.response.SubCategoryResponse(sc.id, sc.name)
            FROM SubCategory sc where sc.category.id = :categoryId
            """)
    List<SubCategoryResponse> getAllSubCategories(Long categoryId);
}
