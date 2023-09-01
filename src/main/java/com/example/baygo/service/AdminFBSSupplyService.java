package com.example.baygo.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.admin.AdminFBSSuppliesResponse;
import com.example.baygo.db.model.enums.FBSSupplyStatus;

import java.time.LocalDate;
import java.util.List;

public interface AdminFBSSupplyService {
    void statusChange(List<Long> supplyIds, FBSSupplyStatus newStatus);

    List<FBSSupplyStatus> getAllStatus();

    PaginationResponse<AdminFBSSuppliesResponse> getAllSupplies(String keyWord, int page, int size);

    long countTotalSupplyQuantity();

    long countSuppliesForDay(LocalDate currentDate);
}
