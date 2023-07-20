package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import com.example.baygo.db.repository.custom.CustomReviewRepository;
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
    public PaginationReviewResponse<ReviewResponse> getAllReviews(Long userId,String keyword, int page, int pageSize) {
        String sqlHigh = """
                    select rev.id,
                           ri.images as review_photo,
                           p.articul as vonder_code,
                           s.barcode as barcode,
                           p.name as product_name,
                           p.brand as product_brand,
                           rev.grade as grade_of_product,
                           sp.color as color_product
                           from reviews rev
                        join products p on p.id = rev.product_id
                        join review_images ri on rev.id = ri.review_id
                        join sub_products sp on p.id = sp.product_id
                        join sizes s on sp.id = s.sub_product_id
                        join sellers s2 on p.seller_id = s2.id
                        join users u on s2.user_id = u.id
                        where rev.grade > 4.5 and u.id = 
                         """+userId+" "+" %s";

        String sqlLow = """
                        select rev.id,
                               ri.images as review_photo,
                               p.articul as vonder_code,
                               s.barcode as barcode,
                               p.name as product_name,
                               p.brand as product_brand,
                               rev.grade as grade_of_product,
                               sp.color as color_product
                        from reviews rev
                                 join products p on p.id = rev.product_id
                                 join review_images ri on rev.id = ri.review_id
                                 join sub_products sp on p.id = sp.product_id
                                 join sizes s on sp.id = s.sub_product_id
                                 join sellers s2 on p.seller_id = s2.id
                                 join users u on s2.user_id = u.id
                        where rev.grade < 4.5 and u.id = 
                        
                        """+userId+" "+" %s";
        log.info("get all review");

        String keywordCondition = "";
        List<Object> params = new ArrayList<>();
        if (keyword != null) {
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            keywordCondition = """
                    and (p.name ilike ? or p.brand ilike ? or p.articul ilike ? or cast(s.barcode as text) ilike ?)
                    """;
        }
        sqlHigh = String.format(sqlHigh, keywordCondition);
        sqlLow = String.format(sqlLow, keywordCondition);

        String countSql = "SELECT COUNT(*) FROM (" + sqlHigh + ") as count_query";
        String countSqlLow = "SELECT COUNT(*) FROM (" + sqlLow + ") as count_query";
        int countHigh = jdbcTemplate.queryForObject(countSql, params.toArray(), Integer.class);
        int countLow = jdbcTemplate.queryForObject(countSqlLow, params.toArray(), Integer.class);
        int totalPageHigh = (int) Math.ceil((double) countHigh / pageSize);
        int totalPageLow = (int) Math.ceil((double) countLow / pageSize);

        sqlHigh = sqlHigh + """
                            LIMIT ? OFFSET ?
                            """;
        sqlLow = sqlLow + """
                          LIMIT ? OFFSET ?
                          """;
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
        return PaginationReviewResponse.<ReviewResponse>builder()
                .foundReviewsHigh(countHigh)
                .elementsHigh(reviewHigh)
                .foundReviewsLow(countLow)
                .elementsLow(reviewLow)
                .totalPagesHigh(totalPageHigh)
                .totalPagesLow(totalPageLow)
                .build();
    }
}
