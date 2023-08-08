package com.example.baygo.api.notification;

import com.example.baygo.db.dto.request.NotificationRequest;
import com.example.baygo.db.dto.response.NotificationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@RestController
public class NotificationApi {
    private final NotificationService service;

    @PostMapping
    public SimpleResponse sendNotificationToSellers(@RequestBody List<Long> buyerIds,
                                                    @RequestBody NotificationRequest request) {
        return service.sendNotificationToSeller(buyerIds, request);

    }

    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadMessagesBySellerId(@RequestParam Long id) {
        return service.getUnreadMessagesBySellerId(id);
    }

    @PostMapping("/{messageId}/read")
    public void markMessageAsRead(@PathVariable Long messageId) {
        service.markMessageAsRead(messageId);
    }

}
