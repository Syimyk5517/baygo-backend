package com.example.baygo.service;

import com.example.baygo.db.dto.request.NotificationRequest;
import com.example.baygo.db.dto.response.NotificationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface NotificationService {
    SimpleResponse sendNotificationToSeller(List<Long> buyerId, NotificationRequest request);
   List<NotificationResponse> getUnreadMessagesBySellerId(Long sellerId);
    void markMessageAsRead(Long messageId);

}
