package com.example.baygo.repository;

import com.example.baygo.db.dto.response.supply.DeliveryDraftResponse;
import com.example.baygo.db.dto.response.supply.ProductBarcodeResponse;
import com.example.baygo.db.model.Supply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    @Query("SELECT NEW com.example.baygo.db.dto.response.ProductBarcodeResponse(s2.barcode,p.name,p.description)" +
            "FROM Supply s " +
            "JOIN SupplyProduct sp ON s.id = sp.supply.id " +
            "JOIN Size s2 ON sp.size.id = s2.id " +
            "JOIN SubProduct sb ON s2.subProduct.id = sb.id " +
            "JOIN Product p ON sb.product.id = p.id " +
            "WHERE s.id = ?1")
    List<ProductBarcodeResponse> getAllBarcodeProducts(Long supplyId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.DeliveryDraftResponse" +
            "(s.id, s.createdAt, s.changedAt, CAST(COUNT(sp.id) AS int), s.quantityOfProducts, s2.user.phoneNumber,s.supplyNumber)" +
            " FROM Supply s JOIN Seller s2 ON s.seller.id = s2.id " +
            "JOIN SupplyProduct sp ON s.id = sp.supply.id " +
            "WHERE  s2.id =:sellerId AND s.isDraft = true " +
            "GROUP BY s.id, s.createdAt, s.changedAt, s.quantityOfProducts, s2.user.phoneNumber, s.supplyNumber")
    Page<DeliveryDraftResponse> getDeliveryDrafts(Pageable pageable, @Param("sellerId") Long sellerId);
}