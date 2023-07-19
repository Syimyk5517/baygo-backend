package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import com.example.baygo.db.repository.CustomReviewRepository;
import com.example.baygo.db.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final CustomReviewRepository customReviewRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginationResponse<ReviewResponse> getAllReviews(String keyword, int page, int pageSize) {
        String sqlHigh = customReviewRepository.getAllHigh();
        String sqlLow = customReviewRepository.getAllLow();
        log.info("get all review");

        String keywordCondition = "";
        List<Object> params = new ArrayList<>();
        if (keyword != null) {
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            keywordCondition = customReviewRepository.keyword();
        }
        sqlHigh = String.format(sqlHigh, keywordCondition);
        sqlLow = String.format(sqlLow, keywordCondition);

        String countSql = "SELECT COUNT(*) FROM (" + sqlHigh + ") as count_query";
        String countSqlLow = "SELECT COUNT(*) FROM (" + sqlLow + ") as count_query";
        int countHigh = jdbcTemplate.queryForObject(countSql, params.toArray(), Integer.class);
        int countLow = jdbcTemplate.queryForObject(countSqlLow, params.toArray(), Integer.class);
        int totalPageHigh = (int) Math.ceil((double) countHigh / pageSize);
        int totalPageLow = (int) Math.ceil((double) countLow / pageSize);

        sqlHigh = sqlHigh + customReviewRepository.limitOffset();
        sqlLow = sqlLow + customReviewRepository.limitOffset();
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);
        List<ReviewResponse> reviewHigh = jdbcTemplate.query(sqlHigh, params.toArray(), (resultSet,i) -> new ReviewResponse(
                resultSet.getLong("id"),
                resultSet.getString("review_photo"),
                resultSet.getString("vonder_code"),
                resultSet.getInt("barcode"),
                resultSet.getString("product_name"),
                resultSet.getString("product_brand"),
                resultSet.getInt("grade_of_product"),
                resultSet.getString("color_product")
        ));
        List<ReviewResponse> reviewLow =jdbcTemplate.query(sqlLow,params.toArray(), (resultSet,i) -> new ReviewResponse(
                resultSet.getLong("id"),
                resultSet.getString("review_photo"),
                resultSet.getString("vonder_code"),
                resultSet.getInt("barcode"),
                resultSet.getString("product_name"),
                resultSet.getString("product_brand"),
                resultSet.getInt("grade_of_product"),
                resultSet.getString("color_product")
        ));
        return PaginationResponse.<ReviewResponse>builder()
                .foundReviewsHigh(countHigh)
                .elementsHigh(reviewHigh)
                .foundReviewsLow(countLow)
                .elementsLow(reviewLow)
                .currentPage(page)
                .totalPagesHigh(totalPageHigh)
                .totalPagesLow(totalPageLow)
                .build();
    }
}
