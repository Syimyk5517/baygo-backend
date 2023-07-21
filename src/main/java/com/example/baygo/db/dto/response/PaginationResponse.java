package com.example.baygo.db.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record PaginationResponse<T> (
        List<T> elements,
        int currentPage,
        int totalPages
){}
