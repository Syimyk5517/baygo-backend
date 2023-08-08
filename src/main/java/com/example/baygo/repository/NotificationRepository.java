package com.example.baygo.repository;

import com.example.baygo.db.dto.response.NotificationResponse;
import com.example.baygo.db.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    @Query("select new com.example.baygo.db.dto.response.NotificationResponse(n.id,n.tittle,n.message," +
//            "n.read,n.createAt)from Notification n where n.id = ?1 and n.read=false ")
//    Optional<NotificationResponse> findBySellerId(Long sellerId);
//
//    @Query("select new com.example.baygo.db.dto.response.NotificationResponse(n.id,n.tittle,n.message,n.read,n.createAt)from" +
//            " Notification n where n.id =: sellerId")
//    List<NotificationResponse>findBySellerIdAndRead(Long sellerId, boolean read);

    List<Notification> getNotificationByBuyerId(Long buyerId);

    @Query("SELECT new com.example.baygo.db.dto.response.NotificationResponse (n.id,n.tittle,n.message,n.read,n.createAt)FROM Notification n WHERE n.read = :readStatus AND n.id=:userId")
    List<NotificationResponse> findByReadStatusByUserContains(Long userId, Boolean readStatus);
    @Query("SELECT n FROM Notification n WHERE n.id = :messageId AND n.buyer.id = :sellerId")
   Optional<NotificationResponse> findByMessageIdAndSellerId(@Param("messageId") Long messageId, @Param("sellerId") Long sellerId);
}