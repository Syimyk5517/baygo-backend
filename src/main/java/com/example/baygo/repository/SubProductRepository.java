package com.example.baygo.repository;

import com.example.baygo.db.model.SubProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubProductRepository extends JpaRepository<SubProduct, Long> {


}
