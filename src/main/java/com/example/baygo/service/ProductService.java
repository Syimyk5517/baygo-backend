package com.example.baygo.service;

import com.example.baygo.db.dto.request.SaveProductRequest;
import com.example.baygo.db.dto.request.UpdateProductDTO;
import com.example.baygo.db.dto.response.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ProductService {
    SimpleResponse saveProduct(SaveProductRequest request);

    PaginationResponse<ProductResponseForSeller> findAll(Long categoryId, String keyWord, String sortBy, boolean ascending, int page, int size);


    PaginationResponseWithQuantity<ProductBuyerResponse> getAllProductsBuyer(String keyWord,
                                                                             List<String> sizes,
                                                                             List<String> compositions,
                                                                             List<String> brands,
                                                                             BigDecimal minPrice,
                                                                             BigDecimal maxPrice,
                                                                             List<String> colors,
                                                                             String filterBy,
                                                                             String sortBy,
                                                                             int page,
                                                                             int pageSize);

    SimpleResponse deleteProduct(Long subProductId);

    UpdateProductDTO getById(Long productId);

    SimpleResponse updateProduct(UpdateProductDTO request);
}
