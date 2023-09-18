package com.example.baygo.db.dto.request;

import java.util.List;

public record NotificationSendRequest (
        List<Long> buyerIds,
        NotificationRequest notificationRequest
){
}
