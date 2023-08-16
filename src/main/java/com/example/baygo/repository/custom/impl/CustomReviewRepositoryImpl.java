package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.PaginationReviewResponse;
import com.example.baygo.db.dto.response.ReviewResponse;
import com.example.baygo.db.dto.response.product.ImagesResponse;
import com.example.baygo.db.dto.response.product.RatingResponse;
import com.example.baygo.db.dto.response.product.ReviewForProduct;
import com.example.baygo.db.dto.response.product.ReviewGetByIdResponse;
import com.example.baygo.repository.custom.CustomReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomReviewRepositoryImpl implements CustomReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginationReviewResponse<ReviewResponse> getAllReviews(Long sellerId, String keyword, int page, int pageSize) {
        String sql = """
                select rev.id,
                       (select ri.images from review_images ri
                        where ri.review_id = rev.id limit 1) as review_image,
                       p.name as product_name,
                       p.articul as articul,
                       sc.name as sub_category,
                       p.brand as product_brand,
                       rev.grade as grade,
                       rev.date_and_time as date_time
                    from reviews rev
                    join products p on p.id = rev.product_id
                    join sub_categories sc on p.sub_category_id = sc.id
                    where p.seller_id = ?
                     """;

        List<Object> params = new ArrayList<>();
        params.add(sellerId);
        if (keyword != null) {
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            sql += """
                    and (p.name ilike ? or p.brand ilike ? or p.articul ilike ? or sc.name ilike ?)
                    """;
        }

        String sqlHigh = sql + " and rev.grade >= 4";
        String sqlLow = sql + " and rev.grade < 4";
        log.info("get all review");

        String countSqlHigh = "SELECT COUNT(*) FROM (" + sqlHigh + ") as count_query";
        String countSqlLow = "SELECT COUNT(*) FROM (" + sqlLow + ") as count_query";
        int countHigh = jdbcTemplate.queryForObject(countSqlHigh, params.toArray(), Integer.class);
        int countLow = jdbcTemplate.queryForObject(countSqlLow, params.toArray(), Integer.class);
        int totalPageHigh = (int) Math.ceil((double) countHigh / pageSize);
        int totalPageLow = (int) Math.ceil((double) countLow / pageSize);

        sqlHigh += " LIMIT ? OFFSET ?";
        sqlLow += " LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);

        List<ReviewResponse> reviewHigh = jdbcTemplate.query(sqlHigh, params.toArray(), (resultSet, i) -> new ReviewResponse(
                resultSet.getLong("id"),
                resultSet.getString("review_image"),
                resultSet.getString("product_name"),
                resultSet.getString("articul"),
                resultSet.getString("sub_category"),
                resultSet.getString("product_brand"),
                resultSet.getInt("grade"),
                resultSet.getString("date_time")
        ));
        List<ReviewResponse> reviewLow = jdbcTemplate.query(sqlLow, params.toArray(), (resultSet, i) -> new ReviewResponse(
                resultSet.getLong("id"),
                resultSet.getString("review_image"),
                resultSet.getString("product_name"),
                resultSet.getString("articul"),
                resultSet.getString("sub_category"),
                resultSet.getString("product_brand"),
                resultSet.getInt("grade"),
                resultSet.getString("date_time")
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

    @Override
    public List<GetAllReviewsResponse> getAllReviewsForSeller(Long sellerId) {
        String sql = """
                select r.id as id,
                       (select spi.images
                       from sub_product_images spi
                       where spi.sub_product_id = sp.id limit 1) as image,
                       r.grade as grade,
                       r.text as text,
                       r.date_and_time as dateAndTime
                    from reviews r
                    join sub_products sp on r.product_id = sp.product_id
                    join products p on sp.product_id = p.id
                    where p.seller_id = ?
                    order by r.date_and_time desc limit 4
                """;
        return jdbcTemplate.query(sql, (resultSet, i) -> new GetAllReviewsResponse(
                resultSet.getLong("id"),
                resultSet.getString("image"),
                resultSet.getInt("grade"),
                resultSet.getString("text"),
                resultSet.getDate("dateAndTime").toLocalDate()
        ), sellerId);
    }

    @Override
    public ReviewGetByIdResponse getAllReviewsOfProduct(Long subProductId, boolean withImages) {
        String query = """
                SELECT round(avg(r.grade)) as totalRating
                FROM reviews r
                JOIN products p on p.id = r.product_id
                JOIN sub_products sp on p.id = sp.product_id
                WHERE sp.id = ?""";

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            double rating = rs.getDouble("totalRating");

            String sql = """
                        SELECT grade, COUNT(*) * 100 / SUM(COUNT(*)) OVER () AS percentage
                        FROM reviews  r
                        JOIN products p on p.id = r.product_id
                        JOIN sub_products sp on p.id = sp.product_id
                        WHERE sp.id = ?
                        GROUP BY grade ORDER BY grade DESC
                    """;
            List<RatingResponse> ratingResponses = jdbcTemplate.query(sql, (resultSet, row) -> {
                int grade = resultSet.getInt("grade");
                int percentage = resultSet.getInt("percentage");
                return new RatingResponse(grade, percentage);
            }, subProductId);

            String imageQuery = """
                        SELECT r.id as imageId, ri.images 
                        FROM reviews r
                        JOIN review_images ri ON r.id = ri.review_id 
                        JOIN products p on p.id = r.product_id
                        JOIN sub_products sp on p.id = sp.product_id
                        WHERE sp.id = ?
                    """;

            List<ImagesResponse> images = jdbcTemplate.query(imageQuery, (r, rowN) -> {
                Long imageId = r.getLong("imageId");
                String imageUrl = r.getString("images");
                return new ImagesResponse(imageId, imageUrl);
            }, subProductId);

            String reviewQuery = """
                        SELECT r.id, r.date_and_time, r.text,
                               b.full_name, b.photo,
                               COALESCE(ri.images, ARRAY[]::text[]) as images
                        FROM reviews r
                        JOIN buyers b ON r.buyer_id = b.id
                        JOIN users u ON b.user_id = u.id
                        JOIN products p ON p.id = r.product_id
                        JOIN sub_products sp ON p.id = sp.product_id
                        LEFT JOIN (
                            SELECT review_id, ARRAY_AGG(images) as images
                            FROM review_images
                            GROUP BY review_id
                        ) ri ON r.id = ri.review_id
                        WHERE sp.id = ?
                        GROUP BY r.id, r.date_and_time, r.text, b.full_name, b.photo, ri.images
                    """;

            if (!withImages) {
                reviewQuery += " HAVING  bool_or(ri.images IS NOT NULL)";
            }

            reviewQuery += " ORDER BY r.date_and_time DESC";

            List<ReviewForProduct> reviewsResponse = jdbcTemplate.query(reviewQuery, (rs1, rowN) -> {
                Long id = rs1.getLong("id");
                LocalDate createdAt = rs1.getObject("date_and_time", LocalDate.class);
                String description = rs1.getString("text");
                String fullName = rs1.getString("full_name");
                String photo = rs1.getString("photo");
                Array imageArray = rs1.getArray("images");
                String[] image = (String[]) imageArray.getArray();
                return new ReviewForProduct(id, fullName, createdAt, photo, description, Arrays.asList(image).toString());
            }, subProductId);

            return ReviewGetByIdResponse.builder()
                    .rating(rating)
                    .ratings(ratingResponses)
                    .images(images)
                    .review(reviewsResponse)
                    .build();
        }, subProductId);
    }
}

