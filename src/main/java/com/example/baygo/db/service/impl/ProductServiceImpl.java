package com.example.baygo.db.service.impl;

import com.example.baygo.db.config.jwt.JwtService;
import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.request.SizeRequest;
import com.example.baygo.db.dto.request.SubProductRequest;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.db.repository.*;
import com.example.baygo.db.repository.custom.CustomProductRepository;
import com.example.baygo.db.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final SubCategoryRepository subCategoryRepository;
    private final SizeRepository sizeRepository;
    private final JwtService jwtService;
    private final CustomProductRepository customProductRepository;

    @Override
    public List<ProductResponseForSeller> findAll(int page, int size) {
        Page<ProductResponseForSeller> pageProduct = customProductRepository.getAllProductOfSeller(PageRequest.of(page, size));
        return pageProduct.getContent();
    }

    @Override
    public List<ProductResponseForSeller> findAllWithFilter(int page, int size) {
        Page<ProductResponseForSeller> pageProducts = customProductRepository.getAllWithFilter(PageRequest.of(page, size));
        return pageProducts.getContent();
    }

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
        product.setSeller(jwtService.getAuthenticate().getSeller());

        for (SubProductRequest subProduct : request.subProducts()) {
            SubProduct subProduct1 = new SubProduct();
            subProduct1.setColorHexCode(subProduct.colorHexCode());
            subProduct1.setColor(subProduct.color());
            subProduct1.setImages(subProduct.images());
            subProduct1.setPrice(subProduct.price());
            subProduct1.setProduct(product);

            for (SizeRequest size : subProduct.sizes()) {
                Size size1 = new Size();
                size1.setSize(size.size());
                size1.setBarcode(size.barcode());
                size1.setSubProduct(subProduct1);
                sizeRepository.save(size1);
            }
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
}
