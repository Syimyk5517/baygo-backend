package com.example.baygo.repository;

import com.example.baygo.db.dto.response.ProductBuyerResponse;
import com.example.baygo.db.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.example.baygo.db.dto.response.ProductBuyerResponse(s.id,sp.id,p.id, p.name, p.description, p.rating, " +
            "count(r),sp.price, coalesce(d.percent, 0)) " +
            "FROM Product p " +
            "join p.reviews r " +
            "join p.subProducts sp " +
            "left join sp.discount d " +
            "join sp.sizes s " +
            "where s.quantity > 0 " +
            "AND (:brand IS NULL OR p.brand IN (:brand)) " +
            "group by s.id, sp.id, s.id, p.id, p.name, p.description, p.rating, sp.price, coalesce(d.percent, 0)")
    Page<ProductBuyerResponse> finds(@Param("brand") List<String> brand, Pageable pageable);

    @Query("select s.images from SubProduct s where s.id = :subProductId")
    List<String> getImageBySubProductId(@Param("subProductId") Long subProductId);

}
