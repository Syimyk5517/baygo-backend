package com.example.baygo.db.dto;

import java.util.List;

public record PaginationResponse<T>(
    List<T> elements,
    int currentPage,
    int pageSize
){ }
