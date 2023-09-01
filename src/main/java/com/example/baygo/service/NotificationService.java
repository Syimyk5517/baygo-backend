package com.example.baygo.service;

import com.example.baygo.db.dto.request.NotificationSendRequest;
import com.example.baygo.db.dto.response.NotificationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface NotificationService {
    SimpleResponse sendNotificationToBuyer(NotificationSendRequest notificationSendRequest);
    List<NotificationResponse> getMyNotifications();
    NotificationResponse getById(Long notificationId);

}
