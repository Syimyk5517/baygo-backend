package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WareHouseRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.FbsWarehouse;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.Warehouse;
import com.example.baygo.repository.FbsWarehouseRepository;
import com.example.baygo.repository.SellerRepository;
import com.example.baygo.repository.WarehouseRepository;
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
    private final JwtService jwtService;


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
                .seller(seller).build();
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
    public SimpleResponse saveShippMethod(ShipmentRequest shipmentRequest) {
        FbsWarehouse existingWareHouse = fbsWarehouseRepository.findById(shipmentRequest.wareHouseId()).orElseThrow(
                () -> new NotFoundException(String.format("Склад %s не найден", shipmentRequest.wareHouseId())));
        existingWareHouse.setTypeOfSupplier(shipmentRequest.typeOfSupplier());
        existingWareHouse.setTypeOfProduct(shipmentRequest.typeOfProduct());
        existingWareHouse.setShippingType(shipmentRequest.shippingType());
        fbsWarehouseRepository.save(existingWareHouse);
        return new SimpleResponse(HttpStatus.OK, "Метод отгрузки %s создан удачно");
    }
}

