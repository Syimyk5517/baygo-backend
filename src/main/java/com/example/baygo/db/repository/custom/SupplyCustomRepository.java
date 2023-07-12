package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.model.enums.SupplyStatus;

import java.util.List;

public interface SupplyCustomRepository {
    List<SuppliesResponse> getAllSuppliesOfSeller(Long currentUserId, String supplyNumber, SupplyStatus status,int page,int pageSize);
}
