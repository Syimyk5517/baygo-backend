package com.example.baygo.service;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.admin.AdminSupplyGetAllResponse;
import com.example.baygo.db.model.enums.SupplyStatus;

import java.time.LocalDate;
import java.util.List;

public interface AdminFBBSupplyService {

    void fbbSupplyChangeStatus(List<Long> supplyIds, SupplyStatus newStatus);

    List<SupplyStatus> getAllStatus();

    PaginationResponse<AdminSupplyGetAllResponse> getAllSupplies(String keyWord, int size, int page);

    long countTotalSupplyQuantity();

    long countSuppliesForDay(LocalDate date);
}
