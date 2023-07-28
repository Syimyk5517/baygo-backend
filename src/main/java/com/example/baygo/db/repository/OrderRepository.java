package com.example.baygo.db.repository;

import com.example.baygo.db.dto.response.RecentOrdersResponse;
import com.example.baygo.db.model.Order;
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

    @Query("select new com.example.baygo.db.dto.response.RecentOrdersResponse(o.id,sp.product.articul,p.name,sp.price,pc,o.status)" +
            " FROM Order o JOIN o.productCount pc JOIN Size s ON KEY(pc) = s join SubProduct sp JOIN Product p where p.seller.id =:sellerId")
    List<RecentOrdersResponse> getAllRecentOrders(Long sellerId);
}