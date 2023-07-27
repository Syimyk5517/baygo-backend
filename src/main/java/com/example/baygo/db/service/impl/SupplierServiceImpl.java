package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.request.SupplierRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Supplier;
import com.example.baygo.db.model.Supply;
import com.example.baygo.db.repository.SupplyRepository;
import com.example.baygo.db.service.SupplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplierServiceImpl implements SupplierService {
    private final SupplyRepository supplyRepository;

    @Override
    public SimpleResponse save(SupplierRequest supplierRequest, Long supplyId) {

        Supply supply = supplyRepository.findById(supplyId)
                .orElseThrow(() -> new NotFoundException("Поставки не найдены!!"));

        Supplier supplier = new Supplier();
        supplier.setNameOfSupplier(supplierRequest.name());
        supplier.setSurnameOfSupplier(supplierRequest.surname());
        supplier.setCarBrand(supplierRequest.carBrand());
        supplier.setCarNumber(supplierRequest.carNumber());
        supplier.setSupplyType(supplierRequest.supplyType());

        supply.setSupplier(supplier);

        supplyRepository.save(supply);

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("поставщик успешно сохранено!!!").build();
    }
}
