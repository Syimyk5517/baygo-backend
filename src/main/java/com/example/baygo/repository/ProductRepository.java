package com.example.baygo.repository;

import com.example.baygo.db.dto.request.UpdateProductDTO;
import com.example.baygo.db.dto.response.HomePageResponse;
import com.example.baygo.db.dto.response.ProductBuyerResponse;
import com.example.baygo.db.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            SELECT NEW com.example.baygo.db.dto.response.ProductBuyerResponse(
               s.id,sp.id,p.id,sp.mainImage, p.name, sp.description,
               p.rating,count(r),sp.price, coalesce(d.percent, 0),
               CASE WHEN f.id IS NOT NULL THEN true ELSE false END
                )
                        FROM Product p
                        JOIN SubProduct sp ON p.id = sp.product.id
                        JOIN Size s ON sp.id = s.subProduct.id
                        LEFT JOIN Review r ON sp.id = r.subProduct.id
                        LEFT JOIN Discount d ON sp.discount.id = d.id
                        LEFT JOIN Buyer b ON b.id = :buyerId
                        LEFT JOIN b.favorites f ON sp.id = f.id
                        WHERE (:keyWord IS NULL OR p.name iLIKE LOWER(CONCAT('%', :keyWord, '%')))
                        AND (:buyerId IS NOT NULL OR :buyerId IS NULL)
                        AND ('' IN :sizes OR s.size IN (:sizes))
                        AND ('' IN :compositions OR p.composition IN (:compositions))
                        AND ('' IN :brands OR p.brand IN (:brands))
                        AND ('' IN :colors OR sp.color IN (:colors))
                        AND (:minPrice IS NULL OR sp.price >= :minPrice)
                        AND (:maxPrice IS NULL OR sp.price <= :maxPrice)
                        AND (
                            (:filterBy = 'Новинки' AND p.dateOfCreate >= CURRENT_DATE - 7)
                            OR (:filterBy = 'Все акции' AND d.percent > 0)
                            OR (
                                :filterBy = 'Бестселлеры' AND s.id IN (
                                   SELECT o.size.id
                                   FROM OrderSize o
                                   WHERE o.size.id = s.id
                                   AND (
                                        SELECT SUM(o.quantity)
                                        FROM OrderSize o
                                        WHERE o.size.id = s.id
                                    ) > 20
                                   AND o.orderStatus <> 'CANCELED'
                                   AND o.order.dateOfOrder >= CURRENT_DATE - 7
                                )
                            )
                         OR (:filterBy IS NULL))
                        AND (sp.isDeleted = FALSE )
                       GROUP BY s.id, sp.id, s.id, p.id, p.name, sp.description, p.rating, sp.price, coalesce(d.percent, 0), f.id
            """)
    @PreAuthorize("hasRole('BUYER') or permitAll()")
    Page<ProductBuyerResponse> finds(Long buyerId,
                                     String keyWord,
                                     List<String> sizes,
                                     List<String> compositions,
                                     List<String> brands,
                                     List<String> colors,
                                     BigDecimal minPrice,
                                     BigDecimal maxPrice,
                                     String filterBy,
                                     Pageable pageable);

    @Query("""
            SELECT NEW com.example.baygo.db.dto.response.HomePageResponse(
            p.id, sp.id, p.name, sp.mainImage
            )
            FROM Product p
            JOIN SubProduct sp ON sp.product.id = p.id
            JOIN Size s ON s.subProduct.id = sp.id
            JOIN OrderSize os ON os.size.id = s.id
              WHERE os.orderStatus <> 'CANCELED'
              AND os.order.dateOfOrder >= CURRENT_DATE - 15
              AND (sp.isDeleted = FALSE )
              GROUP BY p.id, sp.id, s.id
              ORDER BY SUM(os.quantity) DESC
              LIMIT 8
             """)
    List<HomePageResponse> getBestSellersForHomePage();

    @Query("""
             SELECT NEW com.example.baygo.db.dto.response.HomePageResponse(
            p.id, sp.id, p.name, sp.mainImage
            )
            FROM Product p
            JOIN SubProduct sp ON sp.product.id = p.id
            JOIN Discount d ON sp.discount.id = d.id
            WHERE (sp.isDeleted = FALSE )
            ORDER BY d.percent DESC
            LIMIT 8
             """)
    List<HomePageResponse> getHotSalesForHomePage();
    @Query("""
             SELECT NEW com.example.baygo.db.dto.response.HomePageResponse(
            p.id, sp.id, p.name, sp.mainImage
            )
            FROM Product p
            JOIN SubProduct sp ON sp.product.id = p.id
            WHERE sp.isFashion = TRUE
            AND (sp.isDeleted = FALSE )
             """)
    List<HomePageResponse> getFashionProductsForHomePage(Pageable pageable);

    @Query("""
                SELECT NEW com.example.baygo.db.dto.response.HomePageResponse(
                    p.id, sp.id, p.brand, sp.mainImage
                )
                FROM Product p
                JOIN SubProduct sp ON sp.product.id = p.id
                WHERE (p.brand, sp.id) IN (
                    SELECT p3.brand, MAX(sp2.id) AS max_subproduct_id
                    FROM Product p3
                    JOIN SubProduct sp2 ON sp2.product.id = p3.id
                    GROUP BY p3.brand
                )
                AND (sp.isDeleted = FALSE )
                ORDER BY (SELECT COUNT(*) FROM Product p4 WHERE p4.brand = p.brand) DESC, p.brand, sp.mainImage DESC
            """)
    List<HomePageResponse> getPopularBrandsForHomePage(Pageable pageable);

    @Query("""
            SELECT NEW com.example.baygo.db.dto.request.UpdateProductDTO(
            p.id, p.subCategory.id, p.manufacturer, p.brand, p.name, p.season, p.composition)
            FROM Product p
            JOIN SubProduct sp ON sp.product.id = p.id
            WHERE p.id = ?1
            AND (sp.isDeleted = FALSE )
            """)
    UpdateProductDTO getProductById(Long productId);

}
