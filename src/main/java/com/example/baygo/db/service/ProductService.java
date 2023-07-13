package com.example.baygo.db.service;

import com.example.baygo.db.dto.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    PaginationResponse<ProductResponseForSeller> findAll(int page, int size);

    List<ProductResponseForSeller> findAll(PageRequest of);
}
