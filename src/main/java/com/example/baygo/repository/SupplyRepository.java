package com.example.baygo.repository;

import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.model.Supply;
import com.example.baygo.db.model.enums.SupplyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    @Query("select new com.example.baygo.db.dto.response.SuppliesResponse(" +
            "s.id,s.supplyNumber,s.supplyType,s.createdAt,s.quantityOfProducts,s.acceptedProducts,s.commission,s.supplyCost,s.plannedDate,s.actualDate,u.phoneNumber,s.status) " +
            "FROM Supply s " +
            "JOIN Seller s2 ON s.seller.id = s2.id " +
            "JOIN User u ON s2.user.id = u.id WHERE u.id = ?1 " +
            "AND (COALESCE(?2, '') = '' OR s.supplyNumber LIKE CONCAT('%', ?2)) " +
            "AND (?3 IS NULL OR s.status = ?3)")
    Page<SuppliesResponse> getAllSuppliesOfSeller(Long currentUserId, String supplyNumber, SupplyStatus status, Pageable pageable);

    @Query("SELECT NEW com.example.baygo.db.dto.response.SupplyProductResponse(" +
            "   p.id, sp.id, s.id, " +
            "   sp.mainImage, " +
            "   s.barcode, splp.quantity, p.name, sel.vendorNumber, p.brand, s.size, sp.color" +
            " ) " +
            "FROM Supply spl " +
            "JOIN SupplyProduct splp ON spl.id = splp.supply.id " +
            "JOIN Size s ON splp.size.id = s.id " +
            "JOIN SubProduct sp ON s.subProduct.id = sp.id " +
            "JOIN Product p ON p.id = sp.product.id " +
            "JOIN Seller sel ON spl.seller.id = sel.id " +
            "WHERE sel.id = :sellerId AND spl.id = :supplyId " +
            "AND ( :keyWord IS NULL OR " +
            "   CAST(s.barcode AS STRING ) LIKE :keyWord OR " +
            "   LOWER(p.brand) LIKE :keyWord OR " +
            "   LOWER(p.name) LIKE :keyWord OR " +
            "   LOWER(sp.color) LIKE :keyWord)" +
            "GROUP BY p.id, sp.id, s.id, s.barcode, splp.quantity, p.name, sel.vendorNumber, p.brand, s.size, sp.color")
    Page<SupplyProductResponse> getSupplyProducts(
            @Param("sellerId") Long sellerId,
            @Param("supplyId") Long supplyId,
            @Param("keyWord") String keyWord,
            Pageable pageable);











}