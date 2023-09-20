package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.request.fbs.SupplySubProductQuantityRequest;
import com.example.baygo.db.dto.response.QRCodeWithImageResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;
import com.example.baygo.db.dto.response.fbs.SupplyOrderRequest;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.db.model.enums.FBSSupplyStatus;
import com.example.baygo.db.model.enums.OrderStatus;
import com.example.baygo.repository.*;
import com.example.baygo.service.BarcodeService;
import com.example.baygo.service.FBSSupplyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FbsSupplyServiceImpl implements FBSSupplyService {
    private final FbsWarehouseRepository fbsWarehouseRepository;
    private final WarehouseRepository warehouseRepository;
    private final SizeRepository sizeRepository;
    private final FBSSupplyRepository fbsSupplyRepository;
    private final OrderSizeRepository orderSizeRepository;
    private final FBSSupplyRepository repository;
    private final BarcodeService barcodeService;
    private final AccessCardRepository accessCardRepository;
    private final JwtService jwtService;


    @Override
    @Transactional
    public SimpleResponse saveSupply(SupplyRequest supplyRequest) {
        Seller seller = jwtService.getAuthenticate().getSeller();

        Long warehouseId = supplyRequest.wareHouseId();
        FbsWarehouse warehouse = fbsWarehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NotFoundException(String.format("Fbs склад с номером %s не найден", warehouseId)));

        List<SupplySubProductQuantityRequest> supplySizeQuantityRequestList = supplyRequest.supplySizeQuantityRequestList();

        for (SupplySubProductQuantityRequest sizeQuantityRequest : supplySizeQuantityRequestList) {
            Long sizeId = sizeQuantityRequest.sizeId();
            int quantity = sizeQuantityRequest.quantity();

            Size sizeToUpdate = sizeRepository.findById(sizeId)
                    .orElseThrow(() -> new NotFoundException(String.format("Размер с %s supplyId не найден", sizeId)));

            SubProduct subProduct = sizeToUpdate.getSubProduct();
            if (!subProduct.getProduct().getSeller().equals(seller) || !warehouse.getSubProducts().contains(subProduct)) {
                throw new BadRequestException("Этот продукт вам не подлежит");
            }
            sizeToUpdate.setFbsQuantity(sizeToUpdate.getFbsQuantity() + quantity);
        }

        return new SimpleResponse(HttpStatus.OK, "Товары успешно добавлены");
    }

    @Override
    public List<GetAllFbsSupplies> getAllFbsSupplies(boolean isOnAssembly) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        return repository.getAllFbsSupplies(seller.getId(), isOnAssembly);
    }

    @Override
    public GetSupplyWithOrders getSupplyByIdWithOrders(Long supplyId) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        GetSupplyWithOrders result = fbsSupplyRepository.getFbsSupplyById(supplyId, seller.getId());
        result.setOrders(fbsSupplyRepository.getAllFbsOrdersBySupplyId(supplyId, seller.getId()));
        return result;
    }

    @Override
    @Transactional
    public SimpleResponse saveAssemblyTask(SupplyOrderRequest supplyOrderRequest) {
        Seller seller = jwtService.getAuthenticate().getSeller();

        FBSSupply fbsSupply = fbsSupplyRepository.findById(supplyOrderRequest.fbsSupplyId()).orElseThrow(
                () -> new NotFoundException(String.format("Fbs заказы с номером %s не найден", supplyOrderRequest.fbsSupplyId())));
        if (!seller.getFbsSupplies().contains(fbsSupply)) {
            throw new BadRequestException("Этот поставка вам не подлежит");
        }

        for (Long orderSizeId : supplyOrderRequest.orderSizesId()) {
            OrderSize orderSize = orderSizeRepository.findById(orderSizeId).orElseThrow(
                    () -> new NotFoundException(String.format("Fbs заказ с номером - %s не найден", orderSizeId))
            );

            orderSize.setOrderStatus(OrderStatus.ON_ASSEMBLY);
            orderSize.setFbsSupply(fbsSupply);
        }

        return new SimpleResponse(HttpStatus.OK, String.format("Заказы успешно добавились на сборку - %s", fbsSupply.getName()));
    }

    @Override
    public SimpleResponse createSupply(String nameOfSupply) {
        Seller seller = jwtService.getAuthenticate().getSeller();

        FBSSupply fbsSupply = new FBSSupply();
        QRCodeWithImageResponse qrCodeWithImageResponse = barcodeService.generateQrCode();
        fbsSupply.setName(nameOfSupply);
        fbsSupply.setCreatedAt(LocalDateTime.now());
        fbsSupply.setQrCode(qrCodeWithImageResponse.qrCode());
        fbsSupply.setQrCodeImage(qrCodeWithImageResponse.qrCodeImage());
        fbsSupply.setSeller(seller);
        fbsSupply.setFbsSupplyStatus(FBSSupplyStatus.ON_ASSEMBLY);

        fbsSupplyRepository.save(fbsSupply);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("%s - сборочная задание успешно сохранен", fbsSupply.getName()))
                .build();
    }
}