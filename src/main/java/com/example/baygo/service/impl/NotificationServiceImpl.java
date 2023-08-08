package com.example.baygo.service.impl;

import com.example.baygo.db.dto.request.NotificationSendRequest;
import com.example.baygo.db.dto.response.NotificationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Notification;

import com.example.baygo.db.model.User;
import com.example.baygo.repository.BuyerRepository;
import com.example.baygo.repository.NotificationRepository;
import com.example.baygo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final BuyerRepository buyerRepository;

    @Override
    public SimpleResponse sendNotificationToSeller(NotificationSendRequest notificationSendRequest) {
        Notification notification = new Notification();
        notification.setTittle(notificationSendRequest.notificationRequest().tittle());
        notification.setMessage(notificationSendRequest.notificationRequest().message());
        notification.setCreateAt(LocalDateTime.now());
        notification.setRead(false);
        for (Long buyerId : notificationSendRequest.buyerIds()) {
            Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(
                    ()-> new NotFoundException("Buyer not found with id: " + buyerId));

            buyer.addNotification(notification);
            buyerRepository.save(buyer);

        }
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!!!").build();
    }

    @Override
    public List<NotificationResponse> getMyNotifications(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Buyer buyer = buyerRepository.getBuyerByUserId(user.getId());

        List<Notification> notificationByBuyerId = notificationRepository.getNotificationByBuyerId(buyer.getId());

        List<NotificationResponse> notificationResponses = new ArrayList<>();

        for (Notification notification : notificationByBuyerId) {
            NotificationResponse response = NotificationResponse.builder()
                    .id(notification.getId())
                    .creatAt(notification.getCreateAt())
                    .read(notification.getRead())
                    .tittle(notification.getTittle())
                    .message(notification.getMessage())
                    .build();
            notificationResponses.add(response);
        }

        return notificationResponses;
    }

    @Override
    public NotificationResponse getById(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotFoundException("Notification not found with id: " + notificationId));

        if (notification != null){
            notification.setRead(true);
            notificationRepository.save(notification);
        }

        assert notification != null;
        return NotificationResponse.builder()
                .id(notification.getId())
                .read(notification.getRead())
                .tittle(notification.getTittle())
                .message(notification.getMessage())
                .creatAt(notification.getCreateAt())
                .build();
    }
}

