package com.example.baygo.repository;

import com.example.baygo.db.model.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query("SELECT NEW com.example.baygo.db.dto.response.buyer.FavoriteResponse(p.id, sp.mainImage, p.name, p.rating, r.amountOfLike, sp.price) " +
            "FROM Product p " +
            "JOIN p.subProducts sp " +
            "JOIN sp.reviews r " +
            "WHERE r.buyer.id = :buyerId " +
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

}
