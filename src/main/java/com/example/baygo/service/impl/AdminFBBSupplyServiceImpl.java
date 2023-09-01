package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.admin.AdminSupplyGetAllResponse;
import com.example.baygo.db.model.Supply;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.repository.SupplyRepository;
import com.example.baygo.service.AdminFBBSupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFBBSupplyServiceImpl implements AdminFBBSupplyService {
    private final SupplyRepository supplyRepository;

    @Override
    public void fbbSupplyChangeStatus(List<Long> supplyIds, SupplyStatus newStatus) {
        List<Supply> suppliesToUpdate = new ArrayList<>();

        Iterable<Supply> supplies = supplyRepository.findAllById(supplyIds);
        supplies.forEach(suppliesToUpdate::add);
        suppliesToUpdate.forEach(supply -> supply.setStatus(newStatus));

        supplyRepository.saveAll(suppliesToUpdate);
    }

    @Override
    public List<SupplyStatus> getAllStatus() {
        return List.of(SupplyStatus.values());
    }

    @Override
    public PaginationResponse<AdminSupplyGetAllResponse> getAllSupplies(String keyWord, int size, int page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AdminSupplyGetAllResponse> allSuppliesOfAdmin = supplyRepository.getAllSuppliesOfAdmin(keyWord, pageable);
        return new PaginationResponse<>(allSuppliesOfAdmin.getContent(),
                allSuppliesOfAdmin.getNumber() + 1,
                allSuppliesOfAdmin.getTotalPages());
    }

    @Override
    public long countTotalSupplyQuantity() {

        return supplyRepository.countTotalSupplyQuantity();
    }

    @Override
    public long countSuppliesForDay(LocalDate date) {
        return supplyRepository.countSuppliesForDay(date);
    }

}
