package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.SellerProductRequest;
import com.example.baygo.db.dto.request.SellerSizeRequest;
import com.example.baygo.db.dto.request.SellerSubProductRequest;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Product;
import com.example.baygo.db.model.Size;
import com.example.baygo.db.model.SubCategory;
import com.example.baygo.db.model.SubProduct;
import com.example.baygo.repository.SizeRepository;
import com.example.baygo.repository.SubCategoryRepository;
import com.example.baygo.repository.custom.CustomProductRepository;
import com.example.baygo.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public SimpleResponse saveProduct(SellerProductRequest request) {
        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId()).orElseThrow(() -> {
            throw new NotFoundException("Подкатегория с идентификатором: " + request.subCategoryId() + " не найдена!");
        });

        Product product = new Product();
        product.setManufacturer(request.manufacturer());
        product.setBrand(request.brand());
        product.setName(request.name());
        product.setDateOfCreate(LocalDate.now());
        product.setSeason(request.season());
        product.setComposition(request.composition());
        product.setSubCategory(subCategory);
        product.setSeller(jwtService.getAuthenticate().getSeller());

        for (SellerSubProductRequest subProduct : request.subProducts()) {
            SubProduct subProduct1 = new SubProduct();
            subProduct1.setColorHexCode(subProduct.colorHexCode());
            subProduct1.setColor(subProduct.color());
            subProduct1.setMainImage(subProduct.mainImage());
            subProduct1.setImages(subProduct.images());
            subProduct1.setPrice(subProduct.price());
            subProduct1.setDescription(subProduct.description());
            subProduct1.setArticulBG(Integer.parseInt(UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8)));
            subProduct1.setArticulOfSeller(subProduct.articulOfSeller());
            subProduct1.setProduct(product);

            for (SellerSizeRequest size : subProduct.sizes()) {
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
    public PaginationResponse<ProductResponseForSeller> findAll(String status, String keyWord, int page, int size) {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customProductRepository.getAll(sellerId, status, keyWord, page, size);
    }


}
