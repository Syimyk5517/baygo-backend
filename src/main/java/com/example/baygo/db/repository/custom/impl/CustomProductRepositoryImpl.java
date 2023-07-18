package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.repository.custom.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<ProductResponseForSeller> getAllProductOfSeller(Pageable pageable, long sellerId) {
        String sql = """
    with count_cte as (
        select count(*) as total
        from sub_products sp
                 join products pr on sp.product_id = pr.id
                 join sizes s on sp.id = s.sub_product_id
                 join sub_product_images spi on sp.id = spi.sub_product_id
                 join sellers sel on pr.seller_id = sel.id
        where sel.id = ?
    )
    select pr.id as product_id,
           sp.id as sub_product_id,
           s.id as size_id,
           spi.images as sub_product_photo,
           sel.vendor_number as vendor_number,
           pr.articul as product_articul,
           pr.name as product_name,
           pr.brand as product_brand,
           pr.rating as product_rating,
           pr.date_of_change as product_date_of_change,
           sp.color as sub_product_color,
           s.size as sub_product_size,
           s.barcode as sub_product_barcode,
           s.quantity as sub_product_quantity,
           (select total from count_cte) as total_count
    from sub_products sp
             join products pr on sp.product_id = pr.id
             join sizes s on sp.id = s.sub_product_id
             join sub_product_images spi on sp.id = spi.sub_product_id
             join sellers sel on pr.seller_id = sel.id
    where sel.id = ?
    limit ? offset ?
    """;

        List<ProductResponseForSeller> response = new ArrayList<>();
        final int[] total = {0};
        jdbcTemplate.query(sql, new Object[]{sellerId, sellerId, pageable.getPageSize(), pageable.getOffset()}, (rs) -> {
            response.add(new ProductResponseForSeller(
                    rs.getLong("product_id"),
                    rs.getLong("sub_product_id"),
                    rs.getLong("size_id"),
                    rs.getString("sub_product_photo"),
                    rs.getString("vendor_number"),
                    rs.getString("product_articul"),
                    rs.getString("product_name"),
                    rs.getString("product_brand"),
                    rs.getDouble("product_rating"),
                    rs.getDate("product_date_of_change").toLocalDate(),
                    rs.getString("sub_product_color"),
                    rs.getString("sub_product_size"),
                    rs.getInt("sub_product_barcode"),
                    rs.getInt("sub_product_quantity")));
            total[0] = rs.getInt("total_count");
        });

        return new PageImpl<>(response, pageable, total[0]);
    }



    @Override
    public Page<ProductResponseForSeller> getAllWithFilter(Pageable pageable, long sellerId) {
        String sql = """
    with count_cte as (
        select count(*) as total
        from sub_products sp
                 join products pr on sp.product_id = pr.id
                 left join sizes s on sp.id = s.sub_product_id
                 left join sub_product_images spi on sp.id = spi.sub_product_id
                 join sellers sel on pr.seller_id = sel.id
        where sel.id = ?
    )
    select pr.id as product_id,
           sp.id as sub_product_id,
           s.id as size_id,
           spi.images as sub_product_photo,
           sel.vendor_number as vendor_number,
           pr.articul as product_articul,
           pr.name as product_name,
           pr.brand as product_brand,
           pr.rating as product_rating,
           pr.date_of_change as product_date_of_change,
           sp.color as sub_product_color,
           s.size as sub_product_size,
           s.barcode as sub_product_barcode,
           s.quantity as sub_product_quantity,
           (select total from count_cte) as total_count
    from sub_products sp
             join products pr on sp.product_id = pr.id
             left join sizes s on sp.id = s.sub_product_id
             left join sub_product_images spi on sp.id = spi.sub_product_id
             join sellers sel on pr.seller_id = sel.id
    where sel.id = ?
    order by pr.date_of_change desc
    limit ? offset ?
    """;

        List<ProductResponseForSeller> response = new ArrayList<>();
        final int[] total = {0};
        jdbcTemplate.query(sql, new Object[]{sellerId, sellerId, pageable.getPageSize(), pageable.getOffset()}, (rs) -> {
            response.add(new ProductResponseForSeller(
                    rs.getLong("product_id"),
                    rs.getLong("sub_product_id"),
                    rs.getLong("size_id"),
                    rs.getString("sub_product_photo"),
                    rs.getString("vendor_number"),
                    rs.getString("product_articul"),
                    rs.getString("product_name"),
                    rs.getString("product_brand"),
                    rs.getDouble("product_rating"),
                    rs.getDate("product_date_of_change").toLocalDate(),
                    rs.getString("sub_product_color"),
                    rs.getString("sub_product_size"),
                    rs.getInt("sub_product_barcode"),
                    rs.getInt("sub_product_quantity")));
            total[0] = rs.getInt("total_count");
        });

        return new PageImpl<>(response, pageable, total[0]);
    }


}
