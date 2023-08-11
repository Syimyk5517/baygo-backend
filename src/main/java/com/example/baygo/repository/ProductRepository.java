package com.example.baygo.repository;

import com.example.baygo.db.dto.response.ProductBuyerResponse;
import com.example.baygo.db.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            SELECT NEW com.example.baygo.db.dto.response.ProductBuyerResponse(
               s.id,sp.id,sp.product.id, p.name, p.description,
               p.rating,count(r),sp.price, coalesce(d.percent, 0))
                        FROM Product p
                        JOIN SubProduct sp ON p.id = sp.product.id
                        JOIN Size s ON sp.id = s.subProduct.id
                        LEFT JOIN Review r ON p.id = r.product.id
                        LEFT JOIN Discount d ON sp.discount.id = d.id
                        WHERE (:keyWord IS NULL OR p.name iLIKE LOWER(CONCAT('%', :keyWord, '%')))
                        AND ('' IN :sizes OR s.size IN (:sizes))
                        AND ('' IN :compositions OR p.composition IN (:compositions))
                        AND ('' IN :brands OR p.brand IN (:brands))
                        AND ('' IN :colors OR sp.color IN (:colors))
                        AND (:minPrice IS NULL OR sp.price >= :minPrice)
                        AND (:maxPrice IS NULL OR sp.price <= :maxPrice)
                        AND ((:filterBy = 'Новинки' AND p.dateOfCreate >= CURRENT_DATE - 7)
                        OR (:filterBy = 'Все акции' AND d.percent > 0)
                        OR (:filterBy = 'Бестселлеры' AND s.id IN
                          (SELECT s.id FROM Order o WHERE KEY(o.productCount).id = s.id
                           AND (SELECT SUM(VALUE(o2.productCount) ) FROM Order o2 WHERE KEY(o2.productCount).id = s.id)> 20
                           AND o.status <> 'CANCELED' AND o.dateOfOrder >= CURRENT_DATE - 7))
                        OR (:filterBy IS NULL))
                        GROUP BY s.id, sp.id, s.id, p.id, p.name, p.description, p.rating, sp.price, coalesce(d.percent, 0)
            """)
    Page<ProductBuyerResponse> finds(String keyWord,
                                     List<String> sizes,
                                     List<String> compositions,
                                     List<String> brands,
                                     List<String> colors,
                                     BigDecimal minPrice,
                                     BigDecimal maxPrice,
                                     String filterBy,
                                     Pageable pageable);

    @Query("select s.images from SubProduct s where s.id = :subProductId")
    List<String> getImageBySubProductId(@Param("subProductId") Long subProductId);
}
