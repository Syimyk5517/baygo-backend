package com.example.baygo.repository;

import com.example.baygo.db.dto.response.fbs.ProductGetAllResponse;
import com.example.baygo.db.model.FbsWarehouse;
import com.example.baygo.db.model.SubProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FbsWarehouseRepository extends JpaRepository<FbsWarehouse, Long> {
    @Query("SELECT NEW com.example.baygo.db.dto.response.fbs.ProductGetAllResponse(" +
            "s.id ,sp.mainImage,s.barcode,p.name,sp.articulOfSeller,p.brand,s.size,sp.color,CONCAT(fw.street, ' ', fw.houseNumber)) " +
            "from Product p " +
            "join p.subProducts sp " +
            "join sp.sizes s " +
            "join sp.fbsWarehouse fw " +
            "join fw.seller s1  " +
            "where s1.id=:sellerId and fw.id=:wareHouseId")
    List< ProductGetAllResponse> getAllProduct(@Param("sellerId") Long sellerId, @Param("wareHouseId") Long wareHouseId);

    @Modifying
    @Query(value = "delete from fbs_warehouses_sub_products fwsp where fwsp.sub_products_id = ?1", nativeQuery = true)
    void removeSubProductFromWarehouse(Long subProductId);
}