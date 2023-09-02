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
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PackingServiceImpl implements PackingService {
    private final SupplyRepository supplyRepository;

    @Override
    public SimpleResponse repacking(Long supplyId, List<PackingRequest> packingRequests) {
        Supply existingSupply = supplyRepository.findById(supplyId)
                .orElseThrow(() -> new NotFoundException("Подкатегория с идентификатором: " + supplyId + " не найдена!"));

        Map<Integer, Integer> barcodeToQuantityMap = packingRequests.stream()
                .collect(Collectors.toMap(PackingRequest::barcode, PackingRequest::quantity));

        Supply newSupply = new Supply();
        BeanUtils.copyProperties(existingSupply, newSupply);

        List<SupplyProduct> newSupplyProducts = existingSupply.getSupplyProduct().stream()
                .map(existingSupplyProduct -> {
                    int barcode = existingSupplyProduct.getSize().getBarcode();
                    if (barcodeToQuantityMap.containsKey(barcode)) {
                        int newQuantity = existingSupplyProduct.getSize().getFbbQuantity() + barcodeToQuantityMap.get(barcode);
                        existingSupplyProduct.getSize().setFbbQuantity(newQuantity);

                        SupplyProduct newSupplyProduct = new SupplyProduct();
                        BeanUtils.copyProperties(existingSupplyProduct, newSupplyProduct, "id");
                        newSupplyProduct.setSupply(newSupply);
                        return newSupplyProduct;
                    }
                    return existingSupplyProduct;
                })
                .collect(Collectors.toList());

        newSupply.setSupplyProduct(newSupplyProducts);
        supplyRepository.save(newSupply);


        return null;
    }
}
