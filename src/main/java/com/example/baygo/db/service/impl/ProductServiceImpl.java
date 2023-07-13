package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.repository.ProductRepository;
import com.example.baygo.db.repository.custom.CustomProductRepository;
import com.example.baygo.db.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CustomProductRepository customProductRepository;

    @Override
    public List<ProductResponseForSeller> findAll(PageRequest pageRequest) {
        Page<ProductResponseForSeller> page = customProductRepository.getAllProductOfSeller(pageRequest);
        return page.getContent();
    }

    @Override
    public PaginationResponse<ProductResponseForSeller> findAll(int page, int size) {
        return null;
    }
}
