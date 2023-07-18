package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginationResponse<T>(
        int currentPage,
        int totalPage,
        int quantityOfProducts,
        List<T> elements
) {
}
