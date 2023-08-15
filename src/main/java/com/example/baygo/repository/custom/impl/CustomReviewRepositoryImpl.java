package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.PaginationReviewAndQuestionResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import com.example.baygo.repository.custom.CustomReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomReviewRepositoryImpl implements CustomReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginationReviewAndQuestionResponse<ReviewResponse> getAllReviews(Long sellerId, String keyword, boolean isAnswered, int page, int pageSize) {
        String sql = """
                select rev.id review_id,
                       sp.id as sub_product_id,
                       sp.main_image as product_image,
                       p.name as product_name,
                       rev.text comment,
                       rev.grade as grade,
                       sp.articul_of_seller as articul_of_seller,
                       sp.articulbg as articulBG,
                       rev.date_and_time as date_time,
                       rev.answer as answer
                from reviews rev
                join sub_products sp on sp.id = rev.sub_product_id
                join products p on p.id = sp.product_id
                join sub_categories sc on p.sub_category_id = sc.id
                where p.seller_id = ?
                and rev.answer is null
                """;

        String sqlReviewImages = """
                select ri.images
                from review_images ri
                where ri.review_id = ?
                """;

        if (isAnswered) {
            sql = sql.replace("and rev.answer is null", "and rev.answer is not null");
        }

        List<Object> params = new ArrayList<>();
        params.add(sellerId);
        if (keyword != null) {
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            sql += """
                    and (p.name ilike ? or sp.articul_of_seller ilike ? or cast(sp.articulbg as text) ilike ?)
                    """;
        }

        log.info("get all review");

        String countSql = "SELECT COUNT(*) FROM (" + sql + ") as count_query";
        int count = jdbcTemplate.queryForObject(countSql, params.toArray(), Integer.class);
        int totalPages = (int) Math.ceil((double) count / pageSize);

        sql += " LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);

        List<ReviewResponse> reviewsResponse = jdbcTemplate.query(sql, params.toArray(), (resultSet, i) -> new ReviewResponse(
                resultSet.getLong("review_id"),
                resultSet.getLong("sub_product_id"),
                resultSet.getString("product_image"),
                resultSet.getString("product_name"),
                resultSet.getString("comment"),
                resultSet.getInt("grade"),
                resultSet.getString("articul_of_seller"),
                resultSet.getInt("articulBG"),
                resultSet.getString("date_time"),
                resultSet.getString("answer"),
                jdbcTemplate.query(sqlReviewImages, (rs, ii) -> rs.getString("images"), resultSet.getLong("review_id")
                )));
        return PaginationReviewAndQuestionResponse.<ReviewResponse>builder()
                .elements(reviewsResponse)
                .currentPage(page)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public List<GetAllReviewsResponse> getAllReviewsForSeller(Long sellerId) {
        String sql = """
                select r.id as review_id,
                       sp.main_image as image,
                       r.grade as grade,
                       r.text as text,
                       r.date_and_time as dateAndTime
                    from reviews r
                    join sub_products sp on r.sub_product_id = sp.id
                    join products p on sp.product_id = p.id
                    where p.seller_id = ? and r.answer is null
                    order by r.date_and_time desc limit 4
                """;
        return jdbcTemplate.query(sql, (resultSet, i) -> new GetAllReviewsResponse(
                resultSet.getLong("review_id"),
                resultSet.getString("image"),
                resultSet.getInt("grade"),
                resultSet.getString("text"),
                resultSet.getDate("dateAndTime").toLocalDate()
        ), sellerId);
    }
}
