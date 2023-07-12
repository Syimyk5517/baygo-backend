package com.example.baygo.db.repository;

import com.example.baygo.db.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
