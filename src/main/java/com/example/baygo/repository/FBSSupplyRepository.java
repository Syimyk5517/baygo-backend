package com.example.baygo.repository;

import com.example.baygo.db.dto.response.admin.AdminFBSSuppliesResponse;
import com.example.baygo.db.dto.response.fbs.GetAllFbsOrderBySupplyId;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;
import com.example.baygo.db.model.FBSSupply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FBSSupplyRepository extends JpaRepository<FBSSupply, Long> {
    @Query("select new com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies(fs.id, fs.name, fs.createdAt, fs.quantityOfProducts, fs.qrCode) " +
            "FROM FBSSupply fs  " +
            "join fs.seller s " +
            "WHERE s.id = :sellerId")
    List<GetAllFbsSupplies> getAllFbsSupplies(@Param("sellerId") Long sellerId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders(fs.id, fs.name, fs.createdAt, fs.quantityOfProducts, fs.qrCode) " +
            "FROM FBSSupply fs  join fs.seller s " +
            "WHERE fs.id = :supplyId and s.id=:sellerId")
    GetSupplyWithOrders getFbsSupplyById(@Param("supplyId") Long supplyId, @Param("sellerId") Long sellerId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.fbs.GetAllFbsOrderBySupplyId(sp.mainImage, s.barcode, s.fbsQuantity, p.name, s.size, sp.color, sp.price, o.dateOfOrder) " +
            "FROM Order o " +
            "JOIN o.orderSizes os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sp " +
            "JOIN sp.product p " +
            "JOIN os.fbsSupply fs " +
            "JOIN fs.seller s2 " +
            "WHERE fs.id = :supplyId AND s2.id = :sellerId")
    List<GetAllFbsOrderBySupplyId> getAllFbsOrdersBySupplyId(@Param("supplyId") Long supplyId, @Param("sellerId") Long sellerId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.admin.AdminFBSSuppliesResponse(fs.id, fs.qrCode, fs.createdAt, fs.quantityOfProducts, fs.receivedAt, CONCAT(s.firstName, ' ', s.lastName)) " +
            "FROM FBSSupply fs " +
            "JOIN fs.seller s " +
            "WHERE (:keyWord IS NULL OR fs.qrCode ILIKE %:keyWord% OR s.firstName ILIKE %:keyWord% OR s.lastName ILIKE %:keyWord%) " +
            "ORDER BY fs.id DESC")
    Page<AdminFBSSuppliesResponse> getAllAdminSupplies(@Param("keyWord") String keyWord, Pageable pageable);
    @Query("SELECT COUNT (fs)FROM FBSSupply  fs")
    long countTotalSupplyQuantity();
    @Query("SELECT COUNT(fs) FROM FBSSupply fs WHERE DATE(fs.receivedAt) = :currentDate")
    long countFbsSuppliesForDay(@Param("currentDate") LocalDate currentDate);

}