package com.example.baygo.service.impl;

import com.example.baygo.db.dto.request.fbs.SupplyAccessCardRequest;
import com.example.baygo.db.dto.response.BGWarehouseResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.AccessCard;
import com.example.baygo.db.model.Supply;
import com.example.baygo.db.model.Warehouse;
import com.example.baygo.repository.AccessCardRepository;
import com.example.baygo.repository.SupplyRepository;
import com.example.baygo.repository.WarehouseRepository;
import com.example.baygo.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WareHouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final AccessCardRepository accessCardRepository;
    private final SupplyRepository supplyRepository;

    @Override
    public SimpleResponse saveAccess(SupplyAccessCardRequest accessCardRequest) {
//        Warehouse warehouse = warehouseRepository.findById(accessCardRequest.warehouseId()).orElseThrow(() -> new NotFoundException(String.format("Склад %s не найден", accessCardRequest.warehouseId())));
        AccessCard accessCard = AccessCard.builder()
                .driverFirstName(accessCardRequest.driverFirstName())
                .driverLastName(accessCardRequest.driverLastname())
                .carBrand(accessCardRequest.brand())
                .numberOfCar(accessCardRequest.number()).build();
        accessCardRepository.save(accessCard);
//        Supply supply = new Supply();
//        supply.setWarehouse(warehouse);
//        supply.setAccessCard(accessCard);
//        supplyRepository.save(supply);
        return new SimpleResponse(HttpStatus.OK, "Пропуск создан удачно");
    }

    @Override
    public List<BGWarehouseResponse> getAllBGWarehouses() {
        return warehouseRepository.getAllBGWarehouses();
    }
}
