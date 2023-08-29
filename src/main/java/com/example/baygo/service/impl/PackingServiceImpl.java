package com.example.baygo.service.impl;

import com.example.baygo.db.dto.request.PackingRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Supply;
import com.example.baygo.db.model.SupplyProduct;
import com.example.baygo.repository.SupplyRepository;
import com.example.baygo.service.PackingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PackingServiceImpl implements PackingService {
    private final SupplyRepository supplyRepository;

    @Override
    public SimpleResponse repacking(Long supplyId, List<PackingRequest> packingRequests) {
        Supply existingSupply = supplyRepository.findById(supplyId).orElseThrow(() ->
                new NotFoundException("Подкатегория с идентификатором: " + supplyId + " не найдена!"));

        Map<String, Integer> barcodeToQuantityMap = new HashMap<>();

        for (PackingRequest packingRequest : packingRequests) {
            barcodeToQuantityMap.put(packingRequest.barcode(), packingRequest.quantity());
        }

        Supply newSupply = new Supply();
        newSupply.setSupplyNumber(existingSupply.getSupplyNumber());
        newSupply.setSupplyType(existingSupply.getSupplyType());
        newSupply.setCreatedAt(existingSupply.getCreatedAt());
        newSupply.setQuantityOfProducts(existingSupply.getQuantityOfProducts());
        newSupply.setAcceptedProducts(existingSupply.getAcceptedProducts());
        newSupply.setCommission(existingSupply.getCommission());
        newSupply.setSupplyCost(existingSupply.getSupplyCost());
        newSupply.setPlannedDate(existingSupply.getPlannedDate());
        newSupply.setActualDate(existingSupply.getActualDate());
        newSupply.setStatus(existingSupply.getStatus());
        newSupply.setSeller(existingSupply.getSeller());
        newSupply.setWarehouse(existingSupply.getWarehouse());

        List<SupplyProduct> newSupplyProducts = new ArrayList<>();
        for (SupplyProduct existingSupplyProduct : existingSupply.getSupplyProduct()) {
            String barcode = existingSupplyProduct.getSize().getBarcode();
            if (barcodeToQuantityMap.containsKey(barcode)) {
                int newQuantity = existingSupplyProduct.getSize().getFbbQuantity() + barcodeToQuantityMap.get(barcode);
                existingSupplyProduct.getSize().setFbbQuantity(newQuantity);

                SupplyProduct newSupplyProduct = new SupplyProduct();
                newSupplyProduct.setSize(existingSupplyProduct.getSize());
                newSupplyProduct.setSupply(newSupply);
                newSupplyProducts.add(newSupplyProduct);
            } else {
                newSupplyProducts.add(existingSupplyProduct);
            }
        }

        newSupply.setSupplyProduct(newSupplyProducts);
        supplyRepository.save(newSupply);

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Repacking successful").build();
    }
}
