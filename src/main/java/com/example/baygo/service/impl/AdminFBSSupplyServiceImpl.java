package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.admin.AdminFBSSuppliesResponse;
import com.example.baygo.db.model.FBSSupply;
import com.example.baygo.db.model.enums.FBSSupplyStatus;
import com.example.baygo.repository.FBSSupplyRepository;
import com.example.baygo.service.AdminFBSSupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminFBSSupplyServiceImpl implements AdminFBSSupplyService {
    private final FBSSupplyRepository fbsSupplyRepository;
    @Override
    public void statusChange(List<Long> supplyIds, FBSSupplyStatus newStatus) {
        LocalDateTime now = LocalDateTime.now();
        List<FBSSupply> suppliesToUpdate = new ArrayList<>();

        for (Long supplyId : supplyIds) {
            Optional<FBSSupply> optionalSupply = fbsSupplyRepository.findById(supplyId);

            if (optionalSupply.isPresent()) {
                FBSSupply supply = optionalSupply.get();
                supply.setFbsSupplyStatus(newStatus);
                supply.setReceivedAt(now);
                suppliesToUpdate.add(supply);
            }
        }

        fbsSupplyRepository.saveAll(suppliesToUpdate);
    }

    @Override
    public List<FBSSupplyStatus> getAllStatus() {
        return List.of(FBSSupplyStatus.values());
    }

    @Override
    public PaginationResponse<AdminFBSSuppliesResponse> getAllSupplies(String keyWord, int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        Page<AdminFBSSuppliesResponse> allAdminSupplies = fbsSupplyRepository.getAllAdminSupplies(keyWord, pageable);
        return new PaginationResponse<>(allAdminSupplies.getContent(),
                allAdminSupplies.getNumber() + 1,
                allAdminSupplies.getTotalPages());
    }

    @Override
    public long countTotalSupplyQuantity() {
        return fbsSupplyRepository.countTotalSupplyQuantity();
    }

    @Override
    public long countSuppliesForDay(LocalDate currentDate) {
        return fbsSupplyRepository.countFbsSuppliesForDay(currentDate);
    }
}
