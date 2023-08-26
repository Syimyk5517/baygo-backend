package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.*;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.repository.ProductRepository;
import com.example.baygo.repository.SizeRepository;
import com.example.baygo.repository.SubCategoryRepository;
import com.example.baygo.repository.SubProductRepository;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final SubProductRepository subProductRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final SizeRepository sizeRepository;
    private final JwtService jwtService;
    private final CustomProductRepository customProductRepository;
    private final ProductRepository productRepository;

    @Override
    public SimpleResponse saveProduct(SaveProductRequest request) {
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

        for (SaveSubProductRequest subProduct : request.subProducts()) {
            SubProduct newSubProduct = new SubProduct();
            newSubProduct.setColorHexCode(subProduct.colorHexCode());
            newSubProduct.setColor(subProduct.color());
            newSubProduct.setMainImage(subProduct.mainImage());
            newSubProduct.setImages(subProduct.images());
            newSubProduct.setPrice(subProduct.price());
            newSubProduct.setDescription(subProduct.description());
            newSubProduct.setArticulBG(Integer.parseInt(UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8)));
            newSubProduct.setArticulOfSeller(subProduct.articulOfSeller());
            newSubProduct.setHeight(subProduct.height());
            newSubProduct.setWidth(subProduct.width());
            newSubProduct.setLength(subProduct.length());
            newSubProduct.setWeight(subProduct.weight());
            newSubProduct.setProduct(product);

            for (SaveSizeRequest size : subProduct.sizes()) {
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

    @Override
    public PaginationResponseWithQuantity<ProductBuyerResponse> getAllProductsBuyer(String keyWord,
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

        Page<ProductBuyerResponse> allProducts = productRepository.finds(keyWord, sizes, compositions, brands, colors,
                minPrice, maxPrice, filterBy, pageable);

        return PaginationResponseWithQuantity.<ProductBuyerResponse>builder()
                .currentPage(allProducts.getNumber() + 1)
                .totalPages(allProducts.getTotalPages())
                .quantityOfProduct((int) allProducts.getTotalElements())
                .elements(allProducts.getContent())
                .build();
    }

    private List<String> getDefaultIfEmpty(List<String> list) {
        return (list == null || list.isEmpty()) ? List.of("") : list;
    }

    @Override
    public UpdateProductDTO getById(Long productId) {
        Seller seller = jwtService.getAuthenticate().getSeller();
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(String.format("Продукт с ID: %s не найден.", productId)));
        if (!seller.getProducts().contains(product)) {
            throw new BadRequestException(String.format("Продукт с ID: %s не найден в ваших продуктах.", productId));
        }

        UpdateProductDTO productDTO = productRepository.getProductById(productId);
        List<UpdateSubProductDTO> subProductDTO = subProductRepository.getSubProductsByProductId(productId);
        subProductDTO.forEach((x) -> x.setImages(subProductRepository.getImagesSubProductId(x.getSubProductId())));
        subProductDTO.forEach((x) -> x.setSizes(sizeRepository.getSizesBySubProductId(x.getSubProductId())));
        productDTO.setSubProducts(subProductDTO);
        return productDTO;
    }

    @Override
    public SimpleResponse updateProduct(UpdateProductDTO request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new NotFoundException(String.format("Продукт с ID: %s не найден.", request.getProductId())));

        Seller seller = jwtService.getAuthenticate().getSeller();
        if (!seller.getProducts().contains(product)) {
            throw new BadRequestException(String.format("Продукт с ID: %s не найден в ваших продуктах.", request.getProductId()));
        }
        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId()).orElseThrow(
                () -> new NotFoundException(String.format("Под категория с ID: %s не найден.", request.getSubCategoryId())));
        product.setManufacturer(request.getManufacturer());
        product.setBrand(request.getBrand());
        product.setName(request.getName());
        product.setSeason(request.getSeason());
        product.setComposition(request.getComposition());
        product.setSubCategory(subCategory);
        product.setDateOfChange(LocalDate.now());

        for (UpdateSubProductDTO subProduct : request.getSubProducts()) {
            SubProduct oldSubProduct = subProductRepository.findById(subProduct.getSubProductId()).orElseThrow(
                    () -> new NotFoundException(String.format("Под продукт с ID: %s не найден в ваших продуктах.", subProduct.getSubProductId())));
            oldSubProduct.setColorHexCode(subProduct.getColorHexCode());
            oldSubProduct.setColor(subProduct.getColor());
            oldSubProduct.setMainImage(subProduct.getMainImage());
            oldSubProduct.setImages(subProduct.getImages());
            oldSubProduct.setPrice(subProduct.getPrice());
            oldSubProduct.setDescription(subProduct.getDescription());
            oldSubProduct.setArticulOfSeller(subProduct.getArticulOfSeller());
            oldSubProduct.setHeight(subProduct.getHeight());
            oldSubProduct.setWidth(subProduct.getWidth());
            oldSubProduct.setLength(subProduct.getLength());
            oldSubProduct.setWeight(subProduct.getWeight());

            for (UpdateSizeDTO size : subProduct.getSizes()) {
                Size oldSize = sizeRepository.findById(size.sizeId()).orElseThrow(
                        () -> new NotFoundException(String.format("Размер с ID: %s не найден", size.sizeId())));
                oldSize.setSize(size.size());
            }
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Продукт с ID: %s успешно обновлен.".formatted(product.getId()))
                .build();
    }
}