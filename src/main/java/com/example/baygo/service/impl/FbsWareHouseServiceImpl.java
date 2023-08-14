package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.fbs.AccessCardRequest;
import com.example.baygo.db.dto.request.fbs.WareHouseRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.repository.*;
import com.example.baygo.service.FbsWareHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FbsWareHouseServiceImpl implements FbsWareHouseService {
    private final FbsWarehouseRepository fbsWarehouseRepository;
    private final WarehouseRepository warehouseRepository;
    private final SellerRepository sellerRepository;
    private final AccessCardRepository accessCardRepository;
    private final JwtService jwtService;
    private final SupplyRepository supplyRepository;

    @Override
    public SimpleResponse saveWarehouse(WareHouseRequest wareHouseRequest) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        FbsWarehouse fbsWarehouse = FbsWarehouse.builder()
                .name(wareHouseRequest.wareHouseName())
                .country(wareHouseRequest.country())
                .city(wareHouseRequest.city())
                .street(wareHouseRequest.street())
                .indexOfCountry(wareHouseRequest.indexOfCountry())
                .houseNumber(wareHouseRequest.houseNumber())
                .phoneNumber(wareHouseRequest.phoneNumber())
                .workingDay(wareHouseRequest.dayOfWeek())
                .preparingSupply(wareHouseRequest.preparingSupply())
                .assemblyTime(wareHouseRequest.assemblyTime())
                .seller(seller)
                .build();
        fbsWarehouseRepository.save(fbsWarehouse);

        Long selectedWarehouseId = wareHouseRequest.wareHouseId();
        Warehouse warehouse = warehouseRepository.findById(selectedWarehouseId).orElseThrow(
                () -> new NotFoundException(String.format("Склад %s не найден", wareHouseRequest.wareHouseId())));

        if (seller != null) {
                seller.setWarehouseOfReturns(warehouse);
                sellerRepository.save(seller);

                return new SimpleResponse(HttpStatus.OK, String.format("Склад %s успешно сохранены", wareHouseRequest.wareHouseName()));
            } else {
                return new SimpleResponse(HttpStatus.BAD_REQUEST, "Продавец равен нулю");
            }

    }
    @Override
    public SimpleResponse saveAccess(AccessCardRequest accessCardRequest) {

        Warehouse warehouse = warehouseRepository.findById(accessCardRequest.warehouseId()).orElseThrow(
                () -> new NotFoundException(String.format("Склад %s не найден", accessCardRequest.warehouseId())));
        AccessCard accessCard = AccessCard.builder()
                .driverFirstName(accessCardRequest.driverFirstName())
                .driverLastName(accessCardRequest.driverLastname())
                .carBrand(accessCardRequest.brand())
                .numberOfCar(accessCardRequest.number())
                .build();
        accessCardRepository.save(accessCard);

        Supply supply = new Supply();
        supply.setWarehouse(warehouse);
        supply.setAccessCard(accessCard);
        supplyRepository.save(supply);

        return new SimpleResponse(HttpStatus.OK, "Пропуск создан удачно");
    }



}

