package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.fbs.ProductRequest;
import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WarehouseRequest;
import com.example.baygo.db.dto.response.SellerFBSWarehouseResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.FBSWareHouseAddProduct;
import com.example.baygo.db.dto.response.fbs.ProductGetAllResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.repository.FbsWarehouseRepository;
import com.example.baygo.repository.SellerRepository;
import com.example.baygo.repository.SubProductRepository;
import com.example.baygo.repository.WarehouseRepository;
import com.example.baygo.service.FbsWareHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FbsWareHouseServiceImpl implements FbsWareHouseService {
    private final FbsWarehouseRepository fbsWarehouseRepository;
    private final WarehouseRepository warehouseRepository;
    private final SellerRepository sellerRepository;
    private final SubProductRepository subProductRepository;
    private final JwtService jwtService;

    @Override
    public List<SellerFBSWarehouseResponse> getSellerFBSWarehouses() {
        Seller seller = jwtService.getAuthenticate().getSeller();
        return seller.getFbsWarehouse().stream().map(w-> new SellerFBSWarehouseResponse(w.getId(), w.getName())).toList();
    }

    @Override
    public SimpleResponse saveWarehouse(WarehouseRequest warehouseRequest) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        FbsWarehouse fbsWarehouse = FbsWarehouse.builder()
                .name(warehouseRequest.wareHouseName())
                .country(warehouseRequest.country())
                .city(warehouseRequest.city())
                .street(warehouseRequest.street())
                .indexOfCountry(warehouseRequest.indexOfCountry())
                .houseNumber(warehouseRequest.houseNumber())
                .phoneNumber(warehouseRequest.phoneNumber())
                .workingDay(warehouseRequest.dayOfWeek())
                .countOfDaysToPrepareAnOrder(warehouseRequest.countOfDaysToPrepareAnOrder())
                .hourToAssemble(warehouseRequest.hourToAssemble())
                .seller(seller).build();
        fbsWarehouseRepository.save(fbsWarehouse);

        Long fbbWarehouseId = warehouseRequest.fbbWarehouseId();
        Warehouse warehouse = warehouseRepository.findById(fbbWarehouseId).orElseThrow(
                () -> new NotFoundException(String.format("Склад %s не найден", fbbWarehouseId)));
        seller.setWarehouseOfReturns(warehouse);
        sellerRepository.save(seller);

        return new SimpleResponse(HttpStatus.OK, String.format("Склад %s успешно сохранен", warehouseRequest.wareHouseName()));
    }

    @Override
    public SimpleResponse saveShippingMethod(ShipmentRequest shipmentRequest) {
        FbsWarehouse existingWareHouse = fbsWarehouseRepository.findById(shipmentRequest.wareHouseId()).orElseThrow(
                () -> new NotFoundException(String.format("Склад %s не найден", shipmentRequest.wareHouseId())));
        existingWareHouse.setTypeOfSupplier(shipmentRequest.typeOfSupplier());
        existingWareHouse.setTypeOfProduct(shipmentRequest.typeOfProduct());
        existingWareHouse.setShippingType(shipmentRequest.shippingType());
        fbsWarehouseRepository.save(existingWareHouse);
        return new SimpleResponse(HttpStatus.OK, String.format("Метод отгрузки %s создан удачно", shipmentRequest.shippingType()));
    }

    @Override
    public SimpleResponse addProduct(FBSWareHouseAddProduct fbsWareHouseAddProduct) {
        FbsWarehouse existingWareHouse = fbsWarehouseRepository.findById(fbsWareHouseAddProduct.wareHouseId())
                .orElseThrow(() -> new NotFoundException(String.format("Склад %s не найден", fbsWareHouseAddProduct.wareHouseId())));

        List<ProductRequest> productRequests = fbsWareHouseAddProduct.productRequestList();

        List<SubProduct> subProductsToAdd = productRequests.stream()
                .map(productRequest -> {
                    Long subProductId = productRequest.subProductId();

                    return subProductRepository.findById(subProductId)
                            .orElseThrow(() -> new NotFoundException(String.format("Размер %s номером не найден", subProductId)));
                })
                .toList();

        existingWareHouse.getSubProducts().addAll(subProductsToAdd);
        fbsWarehouseRepository.save(existingWareHouse);

        return new SimpleResponse(HttpStatus.OK, "Продукт успешно добавлен");
    }

    @Override
    public List<ProductGetAllResponse> getAllProduct(Long wareHouseId, String keyWord) {
        Seller seller = jwtService.getAuthenticate().getSeller();
        return fbsWarehouseRepository.getAllProduct(seller.getId(), wareHouseId, keyWord);
    }
}