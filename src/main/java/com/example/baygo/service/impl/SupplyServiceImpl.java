package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Supply;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.repository.SupplyRepository;
import com.example.baygo.repository.custom.SupplyCustomRepository;
import com.example.baygo.service.SupplyService;
import com.example.baygo.db.dto.response.SupplyTransitDirectionResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplyServiceImpl implements SupplyService {
    private final JwtService jwtService;
    private final SupplyRepository repository;
    private final SupplyCustomRepository customRepository;

    @Override
    public PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, Boolean isCreatedAt, int page, int pageSize) {
        Long currentUserId = jwtService.getAuthenticate().getId();
        Pageable pageable;

        if (isCreatedAt) {
            pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.ASC, "createdAt");
        } else {
            pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "createdAt");
        }
        Page<SuppliesResponse> suppliesResponses = repository.getAllSuppliesOfSeller(currentUserId, supplyNumber, status, pageable);
        return new PaginationResponse<>(suppliesResponses.getContent(),
                suppliesResponses.getNumber() + 1,
                suppliesResponses.getTotalPages());
    }

    @Override
    public SupplyResponse getSupplyById(Long id) {
        Supply supply = repository.findById(id).orElseThrow(() -> new NotFoundException("Поставки не найдены!!"));
        return SupplyResponse.builder()
                .supplyId(supply.getId())
                .supplyNumber(supply.getSupplyNumber())
                .warehouseName(supply.getWarehouse().getName())
                .supplyType(supply.getSupplyType().getDisplayName())
                .sellerPhoneNumber(supply.getSeller().getUser().getPhoneNumber())
                .supplyCost(supply.getSupplyCost().intValue() == 0 ? "Бесплатно" : supply.getSupplyCost().toString())
                .preliminaryCostOfAcceptance(supply.getSupplyCost())
                .dateOfCreation(supply.getCreatedAt())
                .dateOfChange(supply.getChangedAt())
                .plannedDate(supply.getPlannedDate())
                .actualDate(supply.getActualDate())
                .quantityProductsPcs(supply.getQuantityOfProducts())
                .acceptedPieces(supply.getAcceptedProducts())
                .build();
    }

    @Override
    public PaginationResponse<SupplyProductResponse> getSupplyProducts(Long supplyId, String keyWord, int page, int size) {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customRepository.getSupplyProducts(sellerId, supplyId, keyWord, page, size);
    }

    @Override
    public PaginationResponse<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page) {
        return customRepository.findAllDeliveryFactor(keyword, date, size, page);
    }

    @Override
    public List<SupplyTransitDirectionResponse> getAllTransitDirections(String transitWarehouse, String destinationWarehouse) {
        return customRepository.getAllTransitDirections(transitWarehouse, destinationWarehouse);
    }

    @Override
    public List<SupplyLandingPage> getAllSupplyForLanding() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customRepository.getAllSupplyForLanding(sellerId);
    }
}