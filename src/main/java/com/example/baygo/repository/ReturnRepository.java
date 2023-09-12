package com.example.baygo.repository;

import com.example.baygo.db.dto.response.buyer.ReturnGetByIdResponse;
import com.example.baygo.db.dto.response.buyer.ReturnsResponse;
import com.example.baygo.db.dto.response.seller.SellerReturnGetByIdResponse;
import com.example.baygo.db.dto.response.seller.SellerReturnResponse;
import com.example.baygo.db.model.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReturnRepository extends JpaRepository<Return, Long> {
    @Query("SELECT NEW com.example.baygo.db.dto.response.buyer.ReturnsResponse(" +
            "r.id, s.barcode, sp.mainImage, p.name, s.size, r.productQuantity)" +
            " FROM Buyer b " +
            " JOIN b.orders o " +
            " JOIN o.orderSizes os " +
            " JOIN os.returns r " +
            " JOIN os.size s " +
            " JOIN s.subProduct sp " +
            " JOIN sp.product p " +
            " WHERE b.id = :buyerId ")
    List<ReturnsResponse> getAllReturns(@Param("buyerId") Long buyerId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.buyer.ReturnGetByIdResponse(" +
            "r.id, s.barcode, sp.mainImage, p.name, s.size, os.quantity, r.reason, os.orderStatus,r.returnStatus)" +
            " FROM Buyer b " +
            " JOIN b.orders o " +
            " JOIN o.orderSizes os " +
            " JOIN os.returns r " +
            " JOIN os.size s " +
            " JOIN s.subProduct sp " +
            " JOIN sp.product p " +
            " WHERE b.id = :buyerId AND r.id=:returnId ")
  Optional< ReturnGetByIdResponse> returnGetById(@Param("buyerId") Long buyerId, @Param("returnId") Long returnId);

    @Query("SELECT r.images FROM Buyer b " +
            "JOIN b.orders o " +
            "JOIN o.orderSizes os " +
            "JOIN os.returns r " +
            "WHERE b.id = :buyerId AND r.id = :returnId ")
    List<String> getReturnImageById(@Param("buyerId") Long buyerId, @Param("returnId") Long returnId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.seller.SellerReturnResponse(" +
            "r.id, s.barcode, sb.mainImage, p.name, s.size, r.productQuantity)" +
            "FROM Return r " +
            "JOIN r.orderSize os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sb " +
            "JOIN sb.product p " +
            "JOIN p.seller sel " +
            " WHERE sel.id = :sellerId AND r.returnStatus = 'ACCEPTED'")
    List<SellerReturnResponse> getAllSellerReturns(@Param("sellerId") Long sellerId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.seller.SellerReturnGetByIdResponse(" +
            "r.id,s.barcode,sb.mainImage,p.name,s.size,r.productQuantity,r.reason )" +
            "FROM Return r " +
            "JOIN r.orderSize os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sb " +
            "JOIN sb.product p " +
            "JOIN p.seller sel " +
            " WHERE sel.id = :sellerId AND r.id = :returnId AND r.returnStatus = 'ACCEPTED'")
   Optional<SellerReturnGetByIdResponse>getReturnById(@Param("sellerId") Long sellerId, @Param("returnId") Long returnId);

    @Query("SELECT r.images FROM Return  r " +
            "JOIN r.orderSize os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sb " +
            "JOIN sb.product p " +
            "JOIN p.seller sel " +
            "WHERE sel.id = :sellerId AND r.id = :returnId ")
    List<String> getReturnImageBySeller(@Param("sellerId") Long sellerId, @Param("returnId") Long returnId);
}