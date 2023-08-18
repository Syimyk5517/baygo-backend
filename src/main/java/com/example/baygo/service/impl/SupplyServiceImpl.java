package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.fbs.SupplyOrderRequest;
import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.request.fbs.SupplySizeQuantityRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetByIDFbsSupplyResponse;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.repository.*;
import com.example.baygo.repository.custom.SupplyCustomRepository;
import com.example.baygo.service.SupplyService;
import com.example.baygo.db.dto.response.SupplyTransitDirectionResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplyServiceImpl implements SupplyService {
    private final JwtService jwtService;
    private final SupplyRepository repository;
    private final SupplyCustomRepository customRepository;
    private final FbsWarehouseRepository warehouseRepository;
    private final SizeRepository sizeRepository;
    private final OrderRepository orderRepository;


    @Override
    public PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, int page, int pageSize) {
        Long currentUserId = jwtService.getAuthenticate().getId();
        return customRepository.getAllSuppliesOfSeller(currentUserId, supplyNumber, status, page, pageSize);
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

    @Override
    public SimpleResponse saveSupply(SupplyRequest supplyRequest) {
        Long warehouseId = supplyRequest.wareHouseId();
        List<SupplySizeQuantityRequest> supplySizeQuantityRequestList = supplyRequest.supplySizeQuantityRequestList();

        FbsWarehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NotFoundException(String.format("Fbs склад с номером %s не найден", supplyRequest.wareHouseId())));

        for (SupplySizeQuantityRequest sizeQuantityRequest : supplySizeQuantityRequestList) {
            Long sizeId = sizeQuantityRequest.sizeId();
            int quantity = sizeQuantityRequest.quantity();

            Size size = sizeRepository.findById(sizeId)
                    .orElseThrow(() -> new NotFoundException(String.format("Размер с ID %s не найден", sizeId)));

            warehouse.addProductQuantity(size.getId(), quantity);
        }

        warehouseRepository.save(warehouse);

        return new SimpleResponse(HttpStatus.OK, "Товары успешно добавлены");
    }

    @Override
    public List<GetAllFbsSupplies> getAllFbsSupplies() {
        return repository.getAllFbsSupplies();
    }

    @Override
    public GetSupplyWithOrders getSupplyByIdwithOrders(Long supplyId) {
        GetSupplyWithOrders result = new GetSupplyWithOrders();
        result.setSupply(Collections.singletonList(repository.getFbsSupplyById(supplyId)));
        result.setOrders(repository.getAllFbsOrdersBySupplyId(supplyId));

        return result;
    }

    @Override
    public SimpleResponse saveAssemblyTask(SupplyOrderRequest supplyOrderRequest) {
        orderRepository.findById(supplyOrderRequest.orderId()).orElseThrow(
                ()->new NotFoundException(String.format("Fbs заказы с номером %s не найден",supplyOrderRequest.orderId())));

        return null;
    }
}
