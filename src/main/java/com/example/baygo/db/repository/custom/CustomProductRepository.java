package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CustomProductRepository {
    PaginationResponse<ProductResponseForSeller> getAllProductOfSeller(int page,int size);


    Page<ProductResponseForSeller> getAllProductOfSeller(PageRequest pageRequest);
}
