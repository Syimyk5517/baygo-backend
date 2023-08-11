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
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
                                                                        List<String> sizes,
                                                                        List<String> compositions,
                                                                        List<String> brands,
                                                                        BigDecimal minPrice,
                                                                        BigDecimal maxPrice,
                                                                        List<String> colors,
                                                                        String filterBy,
                                                                        String sortBy,
                                                                        int page,
                                                                        int pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(
                sortBy == null || sortBy.isEmpty() ? Sort.Order.desc("rating") :
                sortBy.equalsIgnoreCase("По увеличению цены") ? Sort.Order.asc("sp.price") :
                        sortBy.equalsIgnoreCase("По уменьшению цены") ? Sort.Order.desc("sp.price") :
                                Sort.Order.desc("rating")
        ));

        sizes = getDefaultIfEmpty(sizes);
        compositions = getDefaultIfEmpty(compositions);
        brands = getDefaultIfEmpty(brands);
        colors = getDefaultIfEmpty(colors);

        Page<ProductBuyerResponse> allProducts = productRepository.finds(keyWord,sizes, compositions, brands, colors,
                                                                    minPrice, maxPrice,filterBy, pageable);


        for (ProductBuyerResponse response : allProducts.getContent()) {
            response.setImage(productRepository.getImageBySubProductId(response.getSubProductId()).get(0));
        }

        return PaginationResponse.<ProductBuyerResponse>builder()
                .currentPage(allProducts.getNumber() + 1)
                .totalPages(allProducts.getTotalPages())
                .quantityOfProducts((int) allProducts.getTotalElements())
                .elements(allProducts.getContent())
                .build();
    }

    private List<String> getDefaultIfEmpty(List<String> list) {
        return (list == null || list.isEmpty()) ? List.of("") : list;
    }

}
