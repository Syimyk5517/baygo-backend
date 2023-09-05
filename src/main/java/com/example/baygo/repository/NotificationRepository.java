package com.example.baygo.repository;

import com.example.baygo.db.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
            SELECT b.notifications FROM Buyer b
            WHERE b.id = :buyerId
            """)
    List<Notification> getNotificationByBuyerId(Long buyerId);
}