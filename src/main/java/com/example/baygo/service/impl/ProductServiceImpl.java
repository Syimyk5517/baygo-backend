package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.request.SizeRequest;
import com.example.baygo.db.dto.request.SubProductRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Product;
import com.example.baygo.db.model.Size;
import com.example.baygo.db.model.SubCategory;
import com.example.baygo.db.model.SubProduct;
import com.example.baygo.repository.ProductRepository;
import com.example.baygo.repository.SizeRepository;
import com.example.baygo.repository.SubCategoryRepository;
import com.example.baygo.repository.custom.CustomProductRepository;
import com.example.baygo.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final SubCategoryRepository subCategoryRepository;
    private final SizeRepository sizeRepository;
    private final JwtService jwtService;
    private final CustomProductRepository customProductRepository;
    private final ProductRepository productRepository;

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

    @Override
    public PaginationResponse<ProductResponseForSeller> findAll(String status, String keyWord, int page, int size) {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customProductRepository.getAll(sellerId, status, keyWord, page, size);
    }

    @Override
    public PaginationResponse<ProductBuyerResponse> getAllProductsBuyer(String keyWord,
                                                                        String sizes,
                                                                        String compositions,
                                                                        List<String> brands,
                                                                        BigDecimal minPrice,
                                                                        BigDecimal maxPrice,
                                                                        String colors,
                                                                        String sortBy,
                                                                        int page,
                                                                        int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        System.out.println("before query");
        System.out.println(brands);
        Page<ProductBuyerResponse> sizePage = productRepository.finds(brands,pageable);
        System.out.println("between");
        for (ProductBuyerResponse response : sizePage.getContent()) {
            response.setImage(productRepository.getImageBySubProductId(response.getSubProductId()).get(0));
        }

        System.out.println("after");

        return PaginationResponse.<ProductBuyerResponse>builder()
                .currentPage(sizePage.getNumber() + 1)
                .totalPages(sizePage.getTotalPages())
                .quantityOfProducts((int) sizePage.getTotalElements())
                .elements(sizePage.getContent())
                .build();
    }
}
