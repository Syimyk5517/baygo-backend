package com.example.baygo.service.impl;

import com.example.baygo.db.dto.request.NotificationRequest;
import com.example.baygo.db.dto.response.NotificationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Notification;

import com.example.baygo.repository.BuyerRepository;
import com.example.baygo.repository.NotificationRepository;
import com.example.baygo.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final BuyerRepository buyerRepository;

    public SimpleResponse sendNotificationToSeller(List<Long> buyerIds, NotificationRequest request) {
        Notification notification = new Notification();
        notification.setTittle(request.tittle());
        notification.setMessage(request.message());
        notification.setCreateAt(LocalDateTime.now());
        notification.setRead(false);
        for (Long buyerId : buyerIds) {
            Optional<Buyer> buyers = buyerRepository.findById(buyerId);
            buyers.ifPresent(buyer -> {
                notification.setBuyer(buyer);
                buyer.addNotification(notification);

            });
        }
        notificationRepository.save(notification);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!!!").build();
    }

    public List<NotificationResponse> getUnreadMessagesBySellerId(Long id) {
        return notificationRepository.findByReadStatusByUserContains(id,false);
    }

    public void markMessageAsRead(Long messageId) {
        Optional<Notification> optionalMessage = notificationRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
         Notification notification = optionalMessage.get();
            notification.setRead(true);
            notificationRepository.save(notification);
        } else {
            throw new EntityNotFoundException("Message not found with id: " + messageId);
        }
    }
}

