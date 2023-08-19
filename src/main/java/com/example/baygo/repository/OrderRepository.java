package com.example.baygo.repository;

import com.example.baygo.db.dto.response.orders.OrderResponse;
import com.example.baygo.db.dto.response.orders.RecentOrdersResponse;
import com.example.baygo.db.model.Order;
import com.example.baygo.db.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("DELETE FROM Order o WHERE o.id = :orderId ")
    void deleteById(@Param("orderId") Long orderId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.orders.RecentOrdersResponse(o.id,sp.articulBG,p.name, sp.price, os.quantity ,os.orderStatus)" +
            " FROM Order o JOIN o.orderSizes os JOIN os.size s JOIN s.subProduct sp JOIN sp.product p WHERE p.seller.id = :sellerId order by o.id desc limit 5")
    List<RecentOrdersResponse> getAllRecentOrders(Long sellerId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.orders.OrderResponse(o.id, s.barcode, b.fullName, sp.price, p.name, o.dateOfOrder, os.orderStatus) " +
            "FROM Order o " +
            "JOIN o.orderSizes os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sp " +
            "JOIN sp.product p " +
            "JOIN o.buyer b " +
            "WHERE p.seller.id = :sellerId " +
            "AND (:keyword IS NULL OR p.name ILIKE %:keyword% OR b.fullName ILIKE %:keyword%) " +
            "AND (:status IS NULL OR os.orderStatus = :status) " +
            "ORDER BY o.dateOfOrder DESC")
    Page<OrderResponse> getAllOrders(Long sellerId, String keyword, OrderStatus status, Pageable pageable);
}





