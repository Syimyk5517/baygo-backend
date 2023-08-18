package com.example.baygo.db.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PaginationReviewAndQuestionResponse<T> {
    private int countOfUnanswered;
    private int countOfArchive;
    private List<T> elements;
    private int currentPage;
    private int totalPages;
}