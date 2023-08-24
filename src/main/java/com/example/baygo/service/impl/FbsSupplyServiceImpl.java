package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.AccessCardRequest;
import com.example.baygo.db.dto.request.fbs.SupplyOrderRequest;
import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.request.fbs.SupplySizeQuantityRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.db.model.enums.FBSSupplyStatus;
import com.example.baygo.repository.*;
import com.example.baygo.service.FBSSupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FbsSupplyServiceImpl implements FBSSupplyService {
    private final FbsWarehouseRepository fbsWarehouseRepository;
    private final WarehouseRepository warehouseRepository;
    private final SizeRepository sizeRepository;
    private final FBSSupplyRepository fbsSupplyRepository;
    private final OrderSizeRepository orderSizeRepository;
    private final FBSSupplyRepository repository;
    private final AccessCardRepository accessCardRepository;
    private final JwtService jwtService;

    @Override
    public SimpleResponse saveSupply(SupplyRequest supplyRequest) {
        Long warehouseId = supplyRequest.wareHouseId();
        List<SupplySizeQuantityRequest> supplySizeQuantityRequestList = supplyRequest.supplySizeQuantityRequestList();

        FbsWarehouse warehouse = fbsWarehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NotFoundException(String.format("Fbs склад с номером %s не найден", supplyRequest.wareHouseId())));

        for (SupplySizeQuantityRequest sizeQuantityRequest : supplySizeQuantityRequestList) {
            Long sizeId = sizeQuantityRequest.sizeId();
            int quantity = sizeQuantityRequest.quantity();

            Size size = sizeRepository.findById(sizeId)
                    .orElseThrow(() -> new NotFoundException(String.format("Размер с ID %s не найден", sizeId)));

          //  warehouse.addProductQuantity(size.getId(), quantity);
        }

        fbsWarehouseRepository.save(warehouse);

        return new SimpleResponse(HttpStatus.OK, "Товары успешно добавлены");
    }

    @Override
    public List<GetAllFbsSupplies> getAllFbsSupplies() {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        return repository.getAllFbsSupplies(seller.getId());
    }

    @Override
    public GetSupplyWithOrders getSupplyByIdWithOrders(Long supplyId) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        GetSupplyWithOrders result = repository.getFbsSupplyById(supplyId, seller.getId());
        result.setOrders(repository.getAllFbsOrdersBySupplyId(supplyId, seller.getId()));
        return result;
    }

    @Override
    public SimpleResponse saveAssemblyTask(SupplyOrderRequest supplyOrderRequest) {
        User user = jwtService.getAuthenticate();

        List<OrderSize> orderSizes = orderSizeRepository.findAllByIdIn(Collections.singletonList(supplyOrderRequest.orderSizeId()));

        if (orderSizes.isEmpty()) {
            throw new NotFoundException(String.format("Fbs заказы с номером %s не найден", supplyOrderRequest.orderSizeId()));
        }

        int totalQuantity = orderSizes.stream().mapToInt(OrderSize::getQuantity).sum();

        Warehouse warehouse = warehouseRepository.findById(supplyOrderRequest.wareHouseId()).orElseThrow(
                () -> new NotFoundException(String.format("Склад %s не найден", supplyOrderRequest.wareHouseId())));

        AccessCard accessCard = null;
        if (supplyOrderRequest.accessCardRequest() != null) {
            AccessCardRequest accessCardRequest = supplyOrderRequest.accessCardRequest();
            accessCard = AccessCard.builder()
                    .driverFirstName(accessCardRequest.driverFirstName())
                    .driverLastName(accessCardRequest.driverLastname())
                    .carBrand(accessCardRequest.brand())
                    .numberOfCar(accessCardRequest.number())
                    .build();
            accessCardRepository.save(accessCard);
        }

        FBSSupply fbsSupply = FBSSupply.builder()
                .name(supplyOrderRequest.nameOfSupply())
                .orderSizes(orderSizes)
                .warehouse(warehouse)
                .fbsSupplyStatus(FBSSupplyStatus.DELIVERY)
                .accessCard(accessCard)
                .createdAt(LocalDateTime.now())
                .quantityOfProducts(totalQuantity)
                .seller(user.getSeller())
                .build();

        fbsSupplyRepository.save(fbsSupply);

        return new SimpleResponse(HttpStatus.OK, String.format("%s сборочная задание успешно сохранен", supplyOrderRequest.nameOfSupply()));
    }
}

