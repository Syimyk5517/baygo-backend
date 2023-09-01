package com.example.baygo.repository;

import com.example.baygo.db.dto.response.SuppliesResponse;

import com.example.baygo.db.dto.response.SupplyProductResponse;

import com.example.baygo.db.dto.response.supply.DeliveryDraftResponse;
import com.example.baygo.db.dto.response.supply.ProductBarcodeResponse;
import com.example.baygo.db.dto.response.supply.SupplySellerProductResponse;

import com.example.baygo.db.model.Supply;
import com.example.baygo.db.model.enums.SupplyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    @Query("SELECT NEW com.example.baygo.db.dto.response.supply.ProductBarcodeResponse(s2.barcode,p.name,p.composition)" +
            "FROM Supply s " +
            "JOIN SupplyProduct sp ON s.id = sp.supply.id " +
            "JOIN Size s2 ON sp.size.id = s2.id " +
            "JOIN SubProduct sb ON s2.subProduct.id = sb.id " +
            "JOIN Product p ON sb.product.id = p.id " +
            "WHERE s.id = ?1")
    List<ProductBarcodeResponse> getAllBarcodeProducts(Long supplyId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.supply.DeliveryDraftResponse" +
            "(s.id, s.createdAt, s.changedAt, CAST(COUNT(sp.id) AS int), s.quantityOfProducts, s2.user.phoneNumber,s.supplyNumber)" +
            " FROM Supply s JOIN Seller s2 ON s.seller.id = s2.id " +
            "JOIN SupplyProduct sp ON s.id = sp.supply.id " +
            "WHERE  s2.id =:sellerId AND s.isDraft = true " +
            "GROUP BY s.id, s.createdAt, s.changedAt, s.quantityOfProducts, s2.user.phoneNumber, s.supplyNumber")
    Page<DeliveryDraftResponse> getDeliveryDrafts(Pageable pageable, @Param("sellerId") Long sellerId);

    @Query("""
              SELECT NEW com.example.baygo.db.dto.response.supply.SupplySellerProductResponse(
                        s.id, sub.mainImage, sc.name, s.barcode, seller.vendorNumber, p.brand, s.size, sub.color)
                        FROM Product p
                        JOIN SubProduct sub ON p.id = sub.product.id
                        JOIN SubCategory sc ON p.subCategory.id = sc.id
                        JOIN Size s ON sub.id = s.subProduct.id
                        JOIN Seller seller ON p.seller.id = seller.id
                        WHERE seller.id = :sellerId
                        AND (CAST(s.barcode AS string ) iLIKE CONCAT("%",:searchWithBarcode)
                        OR (:searchWithBarcode IS NULL ))
                        AND (:category IS NULL OR sc.name = :category)
                        AND (:brand IS NULL OR p.brand = :brand)
            """)
    Page<SupplySellerProductResponse> getSellerProducts(
            @Param("sellerId") Long sellerId,
            @Param("searchWithBarcode") String searchWithBarcode,
            @Param("category") String category,
            @Param("brand") String brand, Pageable pageable);

}