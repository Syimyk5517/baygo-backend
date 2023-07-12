package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.responces.SimpleResponse;
import com.example.baygo.db.model.Brand;
import com.example.baygo.db.model.Category;
import com.example.baygo.db.model.Product;
import com.example.baygo.db.repository.BrandRepository;
import com.example.baygo.db.repository.CategoryRepository;
import com.example.baygo.db.repository.ProductRepository;
import com.example.baygo.db.service.ProductService;
import com.example.baygo.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    public SimpleResponse saveProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> {
            throw new NotFoundException("Категория с идентификатором: " + request.categoryId() + " не найдена!");
        });

        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> {
                    throw new NotFoundException("Бренд с идентификатором: " + request.brandId() + " не найден!");
                });

        Product product = new Product();
        product.setName(request.name());
        product.setManufacturer(request.manufacturer());
        product.setDescription(request.description());
        product.setDateOfCreate(LocalDate.now());
        product.setArticul(UUID.randomUUID().toString().substring(0, 8));
        product.setStyle(request.style());
        product.setSeason(request.season());
        product.setComposition(request.composition());


        return null;
    }
}
