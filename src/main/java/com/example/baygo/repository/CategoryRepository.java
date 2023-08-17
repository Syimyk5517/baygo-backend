package com.example.baygo.repository;

import com.example.baygo.db.dto.response.CategoryResponse;
import com.example.baygo.db.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("""
            SELECT NEW com.example.baygo.db.dto.response.CategoryResponse(c.id, c.name)
           FROM Category c
""")
    List<CategoryResponse> getAllCategories();
}
