package com.example.baygo.repository;

import com.example.baygo.db.model.Warehouse;
import com.example.baygo.dto.response.TransitDirectionResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
  //  List<TransitDirectionResponse> getAllTransits(String name);
}


