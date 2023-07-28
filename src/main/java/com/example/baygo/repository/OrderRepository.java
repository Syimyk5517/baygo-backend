package com.example.baygo.repository;

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

    @Query("SELECT NEW com.example.baygo.db.dto.response.RecentOrdersResponse(o.id,p.articul,p.name, sp.price, VALUE(pc) ,o.status)" +
            " FROM Order o JOIN o.productCount pc JOIN KEY(pc).subProduct sp JOIN sp.product p WHERE p.seller.id =:sellerId order by o.id desc limit 5")
    List<RecentOrdersResponse> getAllRecentOrders(Long sellerId);
}