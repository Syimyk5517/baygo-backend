package com.example.baygo.repository;

import com.example.baygo.db.dto.response.fbs.GetAllFbsOrderBySupplyId;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;
import com.example.baygo.db.model.FBSSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FBSSupplyRepository extends JpaRepository<FBSSupply, Long> {
    @Query("SELECT NEW com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies(fs.id, fs.name, EXTRACT(DATE FROM fs.createdAt), " +
            "(SELECT COUNT(*) FROM OrderSize os WHERE os.fbsSupply.id = fs.id), fs.qrCode, fs.fbsSupplyStatus) " +
            "FROM FBSSupply fs " +
            "JOIN fs.seller s " +
            "WHERE s.id = :sellerId AND (:isOnAssembly = true AND fs.fbsSupplyStatus = 'ON_ASSEMBLY' OR :isOnAssembly = FALSE AND fs.fbsSupplyStatus <> 'ON_ASSEMBLY')")
    List<GetAllFbsSupplies> getAllFbsSupplies(@Param("sellerId") Long sellerId, boolean isOnAssembly);

    @Query("SELECT NEW com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders(fs.id, fs.name, EXTRACT(DATE FROM fs.createdAt), " +
            "(SELECT COUNT(*) FROM OrderSize os WHERE os.fbsSupply.id = fs.id), fs.fbsSupplyStatus, fs.qrCode) " +
            "FROM FBSSupply fs  join fs.seller s " +
            "WHERE fs.id = :supplyId and s.id= :sellerId")
    GetSupplyWithOrders getFbsSupplyById(@Param("supplyId") Long supplyId, @Param("sellerId") Long sellerId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.fbs.GetAllFbsOrderBySupplyId(sp.mainImage, s.barcode, os.fbsQuantity, p.name, s.size, sp.color, os.price, EXTRACT(DATE FROM o.dateOfOrder)) " +
            "FROM Order o " +
            "JOIN o.orderSizes os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sp " +
            "JOIN sp.product p " +
            "JOIN os.fbsSupply fs " +
            "JOIN fs.seller s2 " +
            "WHERE fs.id = :supplyId AND s2.id = :sellerId")
    List<GetAllFbsOrderBySupplyId> getAllFbsOrdersBySupplyId(@Param("supplyId") Long supplyId, @Param("sellerId") Long sellerId);

}