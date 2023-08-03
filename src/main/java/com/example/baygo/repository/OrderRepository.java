package com.example.baygo.repository;

import com.example.baygo.db.dto.response.AnalysisResponse;
import com.example.baygo.db.dto.response.OrderResponse;
import com.example.baygo.db.dto.response.RecentOrdersResponse;
import com.example.baygo.db.model.Order;
import com.example.baygo.db.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("DELETE FROM Order o WHERE o.id = :orderId ")
    void deleteById(@Param("orderId") Long orderId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.RecentOrdersResponse(o.id,p.articul,p.name, sp.price, VALUE(pc) ,o.status)" +
            " FROM Order o JOIN o.productCount pc JOIN KEY(pc).subProduct sp JOIN sp.product p WHERE p.seller.id =:sellerId order by o.id desc limit 5")
    List<RecentOrdersResponse> getAllRecentOrders(Long sellerId);

    @Query("SELECT NEW com.example.baygo.db.dto.response.OrderResponse(o.id, s.barcode, b.fullName, sp.price, p.name, o.dateOfOrder, o.status) " +
            "FROM Order o " +
            "JOIN o.productCount pc " +
            "JOIN Key(pc).subProduct sp " +
            "JOIN sp.product p " +
            "JOIN sp.sizes s " +
            "JOIN o.buyer b " +
            "WHERE p.seller.id = :sellerId " +
            "AND (:keyword IS NULL OR p.name ILIKE %:keyword% OR b.fullName ILIKE %:keyword%) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "ORDER BY o.dateOfOrder DESC")
    Page<OrderResponse> getAllOrders(Long sellerId, String keyword, Status status, Pageable pageable);

    @Query("SELECT NEW com.example.baygo.db.dto.response.AnalysisResponse(" +
            "SUM(CASE WHEN o.dateOfOrder BETWEEN :startDate AND :endDate THEN o.totalPrice ELSE 0 END), " +
            "SUM(CASE WHEN o.dateOfOrder BETWEEN :startDate AND :endDate THEN o.commission ELSE 0 END)) " +
            "FROM Order o " +
            "JOIN Supply s ON o.status = s.status " +
            "WHERE o.warehouse.id = :warehouseId " +
            "AND o.seller.id = :sellerId " +
            "AND o.commission <> 0 " +
            "AND (:nameofTime IS NULL OR " +
            "(LOWER(:nameofTime) = 'day' AND DATE_TRUNC('day', o.dateOfOrder) = CAST(:startDate AS DATE)) OR " +
            "(LOWER(:nameofTime) = 'week' AND o.dateOfOrder >= DATE_TRUNC('week', CAST(:startDate AS DATE)) " +
            "AND o.dateOfOrder <= DATE_TRUNC('week', CAST(:endDate AS DATE)) + interval '6 days') OR " +
            "(LOWER(:nameofTime) = 'month' AND o.dateOfOrder >= DATE_TRUNC('month', CAST(:startDate AS DATE)) " +
            "AND o.dateOfOrder <= DATE_TRUNC('month', CAST(:endDate AS DATE)) + interval '1 month - 1 day'))")
        AnalysisResponse getWeeklyAnalysis(
                @Param("startDate") Date startDate,
                @Param("endDate") Date endDate,
                @Param("warehouseId") Long warehouseId,
                @Param("nameofTime") String nameofTime,
                @Param("sellerId") Long sellerId
        );
    }




