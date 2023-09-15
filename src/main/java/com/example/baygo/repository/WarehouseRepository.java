package com.example.baygo.repository;

import com.example.baygo.db.dto.response.BGWarehouseResponse;
import com.example.baygo.db.dto.response.supply.WarehouseResponse;
import com.example.baygo.db.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query("SELECT new com.example.baygo.db.dto.response.supply.WarehouseResponse(w.id,w.name) FROM Warehouse w")
    List<WarehouseResponse> findAllWarehouses();

    @Query("""
            SELECT new com.example.baygo.db.dto.response.BGWarehouseResponse(w.id, w.region, w.address)
            FROM Warehouse w
            """)
    List<BGWarehouseResponse> getAllBGWarehouses();
}