package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.request.SizeRequest;
import com.example.baygo.db.dto.request.SubProductRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.db.repository.*;
import com.example.baygo.db.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final SubCategoryRepository subCategoryRepository;
    private final SubProductRepository subProductRepository;
    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;


    @Override
    public SimpleResponse saveProduct(ProductRequest request) {
        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId()).orElseThrow(() -> {
            throw new NotFoundException("Подкатегория с идентификатором: " + request.subCategoryId() + " не найдена!");
        });

        Product product = new Product();
        product.setManufacturer(request.manufacturer());
        product.setBrand(request.brand());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setDateOfCreate(LocalDate.now());
        product.setArticul(UUID.randomUUID().toString().substring(0, 8));
        product.setStyle(request.style());
        product.setSeason(request.season());
        product.setComposition(request.composition());
        product.setSubCategory(subCategory);
        product.setSeller(getAuthenticate());

        for (SubProductRequest subProduct : request.subProducts()) {
            SubProduct subProduct1 = new SubProduct();
            subProduct1.setColorHexCode(subProduct.colorHexCode());
            subProduct1.setColor(subProduct.color());
            subProduct1.setImages(subProduct.images());
            subProduct1.setPrice(subProduct.price());
            subProduct1.setVendorNumberOfSeller(subProduct.vendorNumberOfSeller());
            subProduct1.setProduct(product);
            subProductRepository.save(subProduct1);

            for (SizeRequest size : subProduct.sizes()) {
                Size size1 = new Size();
                size1.setSize(size.size());
                size1.setBarcode(size.barcode());
                size1.setSubProduct(subProduct1);
                sizeRepository.save(size1);
            }
            productRepository.save(product);
        }
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Продукт успешно сохранено!!!").build();
    }

    @Override
    public int getBarcode() {
        int hashCode = UUID.randomUUID().hashCode();
        String randomHash = String.valueOf(hashCode);
        if (randomHash.length() > 8) {
            randomHash = randomHash.substring(0, 8);
        }
        return Math.abs(Integer.parseInt(randomHash));
    }

    private Seller getAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("Token has been taken!");
        return sellerRepository.findUserByEmail(login).orElseThrow(() -> {
            log.error("Seller not found!");
            return new NotFoundException("Seller not found!");
        }).getSeller();
    }
}
