package com.example.baygo.repository;

import com.example.baygo.db.dto.request.UpdateSizeDTO;
import com.example.baygo.db.dto.response.buyer.FavoriteResponse;
import com.example.baygo.db.dto.response.product.SizeResponse;
import com.example.baygo.db.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query("SELECT NEW com.example.baygo.db.dto.response.buyer.FavoriteResponse(p.id, bf.id, bf.mainImage, p.name, p.rating, bf.price) " +
            "FROM Buyer b " +
            "JOIN b.favorites bf  " +
            "JOIN bf.product p " +
            "WHERE b.id = :buyerId " +
            "AND (:search IS NULL OR p.name ILIKE %:search% ) " )
    Page<FavoriteResponse> getBuyerFavorites(
            @Param("buyerId") Long buyerId,
            @Param("search") String search,
            Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN TRUE ELSE FALSE END FROM " +
            "SupplyProduct sp " +
            "JOIN sp.size s " +
            "JOIN s.subProduct sub " +
            "WHERE sub.id = ?1")
    Boolean isSupplyProduct(Long subProductId);

    @Query("SELECT CASE WHEN COUNT (o) > 0 THEN TRUE ELSE FALSE END FROM " +
            "OrderSize o " +
            "JOIN o.size s " +
            "JOIN s.subProduct sub " +
            "WHERE sub.id = ?1")
    Boolean isOrderSize(Long subProductId);

    @Query("SELECT NEW com.example.baygo.db.dto.request.UpdateSizeDTO(s.id, s.size) FROM SubProduct sp JOIN sp.sizes s WHERE sp.id = ?1")
    List<UpdateSizeDTO> getSizesBySubProductId(Long subProductId);

    @Query("SELECT CASE WHEN COUNT (sp) > 0 THEN TRUE ELSE FALSE END FROM " +
            "SupplyProduct sp " +
            "JOIN Size s ON s.id = sp.size.id " +
            "WHERE s.id = ?1")
    Boolean isFbb(Long sizeId);

    @Query("""
            SELECT NEW com.example.baygo.db.dto.response.product.SizeResponse(
            s.id, s.size,(s.fbbQuantity + s.fbsQuantity)
            )
            FROM Size s
            WHERE s.subProduct.id = :subProductId
            """)
    List<SizeResponse> findSizeBySubProductId(Long subProductId);
}