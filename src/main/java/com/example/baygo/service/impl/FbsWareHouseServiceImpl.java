package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.fbs.ShipmentRequest;
import com.example.baygo.db.dto.request.fbs.WarehouseRequest;
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
                .preparingSupply(warehouseRequest.preparingSupply())
                .assemblyTime(warehouseRequest.assemblyTime())
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
        return new SimpleResponse(HttpStatus.OK, "Метод отгрузки %s создан удачно");
    }
}