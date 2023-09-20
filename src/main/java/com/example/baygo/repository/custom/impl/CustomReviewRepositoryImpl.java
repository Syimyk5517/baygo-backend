package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.dto.response.PaginationReviewAndQuestionResponse;
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
                SELECT  r.id AS review_id,
                       sp.main_image AS image,
                       r.grade AS grade,
                       r.text AS text,
                       r.date_and_time AS dateAndTime
                    FROM reviews r
                    JOIN sub_products sp ON sp.id = r.sub_product_id
                    JOIN products p ON sp.product_id = p.id
                    WHERE p.seller_id = ? AND r.answer IS NULL
                    ORDER BY r.date_and_time DESC LIMIT 4
                """;
        return jdbcTemplate.query(sql, (resultSet, i) -> new GetAllReviewsResponse(
                resultSet.getLong("review_id"),
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
                JOIN sub_products sp on sp.id = r.sub_product_id
                WHERE sp.id = ?""";

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            double rating = rs.getDouble("totalRating");

            String sql = """
                        SELECT grade, COUNT(*) * 100 / SUM(COUNT(*)) OVER () AS percentage
                        FROM reviews  r
                       JOIN sub_products sp on sp.id = r.sub_product_id
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
                        JOIN sub_products sp on r.sub_product_id = sp.id
                        WHERE sp.id = ?
                    """;

            List<ImagesResponse> images = jdbcTemplate.query(imageQuery, (r, rowN) -> {
                Long imageId = r.getLong("imageId");
                String imageUrl = r.getString("images");
                return new ImagesResponse(imageId, imageUrl);
            }, subProductId);

            String reviewQuery = """
                        SELECT r.id, r.date_and_time,r.grade,sp.color, r.text,
                               b.full_name, b.photo,
                               COALESCE(ri.images, ARRAY[]::text[]) as images
                        FROM reviews r
                        JOIN buyers b ON r.buyer_id = b.id
                        JOIN users u ON b.user_id = u.id
                        
                        JOIN sub_products sp ON r.sub_product_id = sp.id
                        LEFT JOIN (
                            SELECT review_id, ARRAY_AGG(images) as images
                            FROM review_images
                            GROUP BY review_id
                        ) ri ON r.id = ri.review_id
                        WHERE sp.id = ?
                        GROUP BY r.id, r.date_and_time,r.grade,sp.color, r.text, b.full_name, b.photo, ri.images
                    """;

            if (!withImages) {
                reviewQuery += " HAVING  bool_or(ri.images IS NOT NULL)";
            }

            reviewQuery += " ORDER BY r.date_and_time DESC";

            List<ReviewForProduct> reviewsResponse = jdbcTemplate.query(reviewQuery, (rs1, rowN) -> {
                Long id = rs1.getLong("id");
                LocalDate createdAt = rs1.getObject("date_and_time", LocalDate.class);
                int grade=rs1.getInt("grade");
                String color=rs1.getString("color");
                String description = rs1.getString("text");
                String fullName = rs1.getString("full_name");
                String photo = rs1.getString("photo");
                Array imageArray = rs1.getArray("images");
                String[] image = (String[]) imageArray.getArray();
                return new ReviewForProduct(id, fullName, createdAt,grade,color, photo, description, Arrays.asList(image).toString());
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

