package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.fbb.*;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.deliveryFactor.WarehouseCostResponse;
import com.example.baygo.db.dto.response.supply.DeliveryDraftResponse;
import com.example.baygo.db.dto.response.supply.ProductBarcodeResponse;
import com.example.baygo.db.dto.response.supply.SupplySellerProductResponse;
import com.example.baygo.db.dto.response.supply.WarehouseResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.model.enums.SupplyType;
import com.example.baygo.repository.*;
import com.example.baygo.repository.custom.SupplyCustomRepository;
import com.example.baygo.service.SupplyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplyServiceImpl implements SupplyService {
    private final JwtService jwtService;
    private final SupplyRepository repository;
    private final SupplyCustomRepository customRepository;
    private final SizeRepository sizeRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplyProductRepository supplyProductRepository;
    private final ProductPackagesRepository productPackagesRepository;


    @Override
    public PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(String supplyNumber, SupplyStatus status, Boolean isAscending, int page, int pageSize) {
        Long currentUserId = jwtService.getAuthenticate().getId();
        Pageable pageable = PageRequest.of(page - 1, pageSize, isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, "createdAt");
        Page<SuppliesResponse> suppliesResponses = repository.getAllSuppliesOfSeller(currentUserId, supplyNumber, status, pageable);
        return new PaginationResponse<>(suppliesResponses.getContent(),
                suppliesResponses.getNumber() + 1,
                suppliesResponses.getTotalPages());
    }

    @Override
    public SupplyResponse getSupplyById(Long id) {
        Supply supply = repository.findById(id).orElseThrow(() -> new NotFoundException("Поставки не найдены!!"));
        return SupplyResponse.builder()
                .supplyId(supply.getId())
                .supplyNumber(supply.getSupplyNumber())
                .warehouseName(supply.getWarehouse().getName())
                .supplyType(supply.getSupplyType().getDisplayName())
                .sellerPhoneNumber(supply.getSeller().getUser().getPhoneNumber())
                .supplyCost(supply.getSupplyCost().intValue() == 0 ? "Бесплатно" : supply.getSupplyCost().toString())
                .preliminaryCostOfAcceptance(supply.getSupplyCost())
                .dateOfCreation(supply.getCreatedAt())
                .dateOfChange(supply.getChangedAt())
                .plannedDate(supply.getPlannedDate())
                .actualDate(supply.getActualDate())
                .quantityProductsPcs(supply.getQuantityOfProducts())
                .acceptedPieces(supply.getAcceptedProducts())
                .build();
    }

    @Override
    public PaginationResponse<SupplyProductResponse> getSupplyProducts(Long supplyId, String keyWord, int page, int size) {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SupplyProductResponse> supplyProductPage = repository.getSupplyProducts(sellerId, supplyId, keyWord,pageable);

        return PaginationResponse.<SupplyProductResponse>builder()
                .elements(supplyProductPage.getContent())
                .currentPage(supplyProductPage.getNumber() + 1)
                .totalPages(supplyProductPage.getTotalPages())
                .build();
    }
    @Override
    public PaginationResponse<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page) {
        return customRepository.findAllDeliveryFactor(keyword, date, size, page);
    }

    @Override
    public List<SupplyTransitDirectionResponse> getAllTransitDirections(String transitWarehouse, String destinationWarehouse) {
        return customRepository.getAllTransitDirections(transitWarehouse, destinationWarehouse);
    }

    @Override
    public List<SupplyLandingPage> getAllSupplyForLanding() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customRepository.getAllSupplyForLanding(sellerId);
    }


    @Override
    public PaginationResponse<DeliveryDraftResponse> getDeliveryDrafts(int pageSize, int page) {
        User user = jwtService.getAuthenticate();
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<DeliveryDraftResponse> draftResponsePage = repository.getDeliveryDrafts(pageable, user.getSeller().getId());
        return new PaginationResponse<>(
                draftResponsePage.getContent(),
                draftResponsePage.getNumber() + 1,
                draftResponsePage.getTotalPages()
        );
    }

    @Override
    public SimpleResponse deleteDeliveryDraft(Long supplyId) {
        Supply supply = repository.findById(supplyId).orElseThrow(
                () -> new NotFoundException(String.format("Поставка с идентификатором %s не найден.", supplyId)));
        repository.delete(supply);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Поставка с идентификатором %s успешно удален", supply.getId())).build();
    }

    @Override
    public PaginationResponse<SupplySellerProductResponse> getSellerProducts(
            String searchWithBarcode, Long subCategoryId, int page, int pageSize) {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<SupplySellerProductResponse> productResponses = repository.getSellerProducts(sellerId, searchWithBarcode, subCategoryId, pageable);
        return new PaginationResponse<>(productResponses.getContent(),
                productResponses.getNumber() + 1,
                productResponses.getTotalPages());
    }

    @Override
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAllWarehouses();
    }

    @Override
    public SimpleResponse createSupply(FBBSupplyRequest supplyRequest) {
        User user = jwtService.getAuthenticate();
        Warehouse warehouse = warehouseRepository.findById(supplyRequest.warehouseId()).orElseThrow(
                () -> new NotFoundException(String.format("Склад c идентикатом %s не найден.", supplyRequest.warehouseId())));
        Supply supply = new Supply();
        int quantityOfProducts = 0;
        for (SupplyChooseRequest chooseRequest : supplyRequest.supplyChooseRequests()) {
            Size size = sizeRepository.findById(chooseRequest.sizeId()).orElseThrow(
                    () -> new NotFoundException("Продукт не найден"));
            quantityOfProducts += chooseRequest.quantityProduct();

            SupplyProduct supplyProducts = SupplyProduct.builder()
                    .size(size)
                    .supply(supply)
                    .quantity(chooseRequest.quantityProduct()).build();
            supply.addSupplyProduct(supplyProducts);
        }

        UUID uuid = UUID.randomUUID();
        long positiveValue = Math.abs(uuid.getLeastSignificantBits()) % 100000000;
        String supplyNumber = String.format("%08d", positiveValue);

        supply.setQuantityOfProducts(quantityOfProducts);
        supply.setSeller(user.getSeller());
        supply.setIsDraft(true);
        supply.setWarehouse(warehouse);
        supply.setPlannedDate(supplyRequest.plannedDate());
        supply.setSupplyType(supplyRequest.supplyType());
        supply.setCreatedAt(LocalDate.now());
        supply.setChangedAt(LocalDate.now());
        supply.setSupplyNumber(supplyNumber);
        repository.save(supply);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("""
                        Поставка с идентификатором %s успешно создана!!
                        Номер поставки %s
                        """, supply.getId(), supply.getSupplyNumber())).build();
    }

    @Override
    public List<WarehouseCostResponse> getAllWarehouseCost(Long warehouseId, SupplyType supplyType) {
        return customRepository.getAllWarehouseCostResponse(warehouseId, supplyType);
    }

    @Override
    public List<ProductBarcodeResponse> getAllBarcodeProducts(Long supplyId) {
        return repository.getAllBarcodeProducts(supplyId);
    }

    @Override
    public SimpleResponse willCompleteTheDelivery(SupplyWrapperRequest supplyWrapperRequest) {
        Supply supply = repository.findById(supplyWrapperRequest.supplyId()).orElseThrow(
                () -> new NotFoundException(String.format("Поставка с идентификатором %s не найден.", supplyWrapperRequest.supplyId())));
        supply.setSupplyCost(supplyWrapperRequest.supplyCost());
        supply.setCommission(supplyWrapperRequest.commission());
        supply.setStatus(SupplyStatus.PLANNED);
        supply.setPlannedDate(supplyWrapperRequest.plannedDate());
        supply.setIsDraft(false);
        for (ProductPackagesRequest packagesRequest : supplyWrapperRequest.productPackagesRequests()) {
            Map<SupplyProduct, Integer> productCounts = new HashMap<>();
            for (NumberOfProductsRequest numberOfProductsRequest : packagesRequest.numberOfProductsRequests()) {
                SupplyProduct supplyProduct = supplyProductRepository.findBySupplyProductWithBarcode(numberOfProductsRequest.barcodeProduct(), supply.getId()).orElseThrow(
                        () -> new NotFoundException("Товар с таким баркодом и в этом поставке не найден."));
                productCounts.put(supplyProduct, numberOfProductsRequest.quantityProduct());
            }
            ProductPackages productPackages = new ProductPackages();
            productPackages.setPackageNumber(packagesRequest.packageNumber());
            productPackages.setProductCounts(productCounts);
            productPackagesRepository.save(productPackages);
        }
        AccessCard accessCard = AccessCard.builder()
                .deliveryPass(supplyWrapperRequest.supplyDeliveryRequest().deliveryPass())
                .driverFirstName(supplyWrapperRequest.supplyDeliveryRequest().driverName())
                .driverLastName(supplyWrapperRequest.supplyDeliveryRequest().driverSurname())
                .numberOfCar(supplyWrapperRequest.supplyDeliveryRequest().carNumber())
                .carBrand(supplyWrapperRequest.supplyDeliveryRequest().carBrand())
                .supplyType(supplyWrapperRequest.supplyDeliveryRequest().supplyType())
                .supply(supply)
                .numberOfSeats(supplyWrapperRequest.supplyDeliveryRequest().numberOfSeats()).build();
        supply.setAccessCard(accessCard);
        repository.save(supply);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Поставка с идентификатором %s успешно сохранено!!!", supply.getId())).build();
    }


}
