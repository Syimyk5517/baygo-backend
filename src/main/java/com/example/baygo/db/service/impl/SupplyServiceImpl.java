package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.SupplyResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Supply;
import com.example.baygo.db.repository.SupplyRepository;
import com.example.baygo.db.repository.customRepository.SupplyCustomRepository;
import com.example.baygo.db.service.SupplyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository repository;
    private final SupplyCustomRepository customRepository;

    @Override
    public SupplyResponse getSupplyById(Long id) {
        Supply supply = repository.findById(id).orElseThrow(() -> new NotFoundException("Поставки не найдены!!"));
        return SupplyResponse.builder()
                .supplyId(supply.getId())
                .supplyNumber(supply.getSupplyNumber())
                .warehouseName(supply.getWarehouse().getName())
                .supplyType(supply.getSupplyType())
                .sellerPhoneNumber(supply.getSeller().getUser().getPhoneNumber())
                .supplyCost(supply.getSupplyCost())
                .preliminaryCostOfAcceptance(supply.getSupplyCost())//
                .dateOfCreation(supply.getCreatedAt())
                .dateOfChange(supply.getCreatedAt())//
                .plannedDate(supply.getPlannedDate())
                .actualDate(supply.getActualDate())
                .quantityProductsPcs(supply.getQuantityOfProducts())
                .acceptedPieces(supply.getAcceptedProducts())
        .build();
    }


    @Override
    public List<SupplyProductResponse> searchSupplyProducts(String keyWord,int page,int size) {

        return null;
    }
}
