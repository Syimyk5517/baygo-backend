package com.example.baygo.repository;

import com.example.baygo.db.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT * FROM notifications nt " +
            "JOIN notifications_buyers nb ON nt.id = nb.notification_id " +
            "WHERE nb.buyer_id = :buyerId " +
            "ORDER BY nt.create_at DESC", nativeQuery = true)
    List<Notification> getNotificationByBuyerId(Long buyerId);
}