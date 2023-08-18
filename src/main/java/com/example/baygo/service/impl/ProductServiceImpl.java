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
            SubProduct newSubProduct = new SubProduct();
            newSubProduct.setColorHexCode(subProduct.colorHexCode());
            newSubProduct.setColor(subProduct.color());
            newSubProduct.setMainImage(subProduct.mainImage());
            newSubProduct.setImages(subProduct.images());
            newSubProduct.setPrice(subProduct.price());
            newSubProduct.setDescription(subProduct.description());
            newSubProduct.setArticulBG(Integer.parseInt(UUID.randomUUID().toString().replaceAll("[^0-9]","").substring(0,8)));
            newSubProduct.setArticulOfSeller(subProduct.articulOfSeller());
            newSubProduct.setHeight(subProduct.height());
            newSubProduct.setWidth(subProduct.width());
            newSubProduct.setLength(subProduct.length());
            newSubProduct.setWeight(subProduct.weight());
            newSubProduct.setProduct(product);

            for (SellerSizeRequest size : subProduct.sizes()) {
                Size newSize = new Size();
                newSize.setSize(size.size());
                newSize.setBarcode(size.barcode());
                newSize.setSubProduct(newSubProduct);
                sizeRepository.save(newSize);
            }
        }
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Продукт успешно сохранено!!!").build();
    }

    @Override
    public PaginationResponse<ProductResponseForSeller> findAll(Long categoryId, String keyWord, String sortBy, boolean ascending, int page, int size) {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customProductRepository.getAll(sellerId, categoryId, keyWord, sortBy, ascending, page, size);
    }
}
