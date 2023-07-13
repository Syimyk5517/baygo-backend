package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.model.enums.SupplyStatus;

import java.util.List;

public interface SupplyService {
    PaginationResponse<List<SuppliesResponse>> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, int page, int pageSize);

}
