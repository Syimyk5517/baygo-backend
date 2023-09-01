package com.example.baygo.repository;

import com.example.baygo.db.dto.response.admin.AdminFBBOrderResponse;
import com.example.baygo.db.dto.response.admin.AdminFBSOrderResponse;
import com.example.baygo.db.model.OrderSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderSizeRepository extends JpaRepository<OrderSize, Long> {
    List<OrderSize> findAllByIdIn(List<Long> singletonList);

    @Query("SELECT NEW com.example.baygo.db.dto.response.admin.AdminFBBOrderResponse(" +
            "os.id, os.qrCode, p.name, os.quantity, CONCAT(sel.firstName, ' ', sel.lastName), " +
            "o.dateOfOrder, os.dateOfReceived, os.orderStatus,CONCAT(c.firstName,' ',c.lastName),c.address,c.phoneNumber) " +
            "FROM Order o " +
            "JOIN o.orderSizes os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sb " +
            "JOIN sb.product p " +
            "JOIN p.seller sel " +
            "JOIN o.customer c " +
            "WHERE (:keyWord IS NULL OR p.name LIKE %:keyWord% OR sel.firstName LIKE %:keyWord% OR sel.lastName LIKE %:keyWord%) " +
            "AND os.isFbsOrder = FALSE")
    Page<AdminFBBOrderResponse> getAllFBBOrder(@Param("keyWord") String keyWord, Pageable pageable);

    @Query("SELECT COUNT(os)FROM OrderSize os  WHERE os.isFbsOrder=FALSE ")
    long countFBBOrders();

    @Query("SELECT NEW com.example.baygo.db.dto.response.admin.AdminFBSOrderResponse(" +
            "o.id, os.qrCode, os.quantity, p.name, os.orderStatus, o.dateOfOrder, os.dateOfReceived,CONCAT(c.firstName,' ',c.lastName),c.address,c.phoneNumber) " +
            "FROM Order o " +
            "JOIN o.orderSizes os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sb " +
            "JOIN sb.product p " +
            "JOIN p.seller sel  JOIN o.customer c " +
            "WHERE (:keyWord IS NULL OR p.name LIKE %:keyWord% ) AND os.isFbsOrder=TRUE " +
            "ORDER BY o.id DESC")
    Page<AdminFBSOrderResponse> getAllFBSOrders(@Param("keyWord") String keyWord, Pageable pageable);

    @Query("SELECT COUNT(os)FROM OrderSize  os WHERE os.isFbsOrder=TRUE ")
    long countFBSOrders();
}