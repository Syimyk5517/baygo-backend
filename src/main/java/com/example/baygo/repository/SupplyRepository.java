package com.example.baygo.repository;

import com.example.baygo.db.dto.response.fbs.GetAllFbsOrderBySupplyId;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetByIDFbsSupplyResponse;
import com.example.baygo.db.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    @Query("select new com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies(fs.id,fs.name,fs.create_at,fs.total_quantity,fs.qr_code )" +
            "from Fbs_supplies fs")
    List<GetAllFbsSupplies> getAllFbsSupplies();
    @Query("select new com.example.baygo.db.dto.response.fbs.GetByIDFbsSupplyResponse(fs.id,fs.name,fs.create_at,fs.total_quantity,fs.qr_code )" +
            "from Fbs_supply fs" +
            "fs where fs.id=:supplyid")
    GetByIDFbsSupplyResponse getFbsSupplyById(@Param("supplyId")Long supplyId);

    @Query("select new com.example.baygo.db.dto.response.fbs.GetAllFbsOrderBySupplyId(sp.mainImage, sp.barcode,  p.name,s.fbs_quantity,s.size,  sp.color, o.dateOfOrder)" +
            "from Order  o " +
            "join o.sizes s " +
            "where fs.id=:supplyId" )
    List<GetAllFbsOrderBySupplyId> getAllFbsOrdersBySupplyId(@Param("supplyId")Long supplyId);
}