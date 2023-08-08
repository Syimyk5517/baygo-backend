package com.example.baygo.api.notification;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import com.example.baygo.db.dto.request.NotificationSendRequest;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Send notification!", description = "This method for sending notification!")
    @PostMapping
    public SimpleResponse sendNotificationToSellers(@RequestBody NotificationSendRequest notificationSendRequest){
        return service.sendNotificationToSeller(notificationSendRequest);
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @Operation(summary = "Get all", description = "This method for get all my notifications")
    @GetMapping
    public List<NotificationResponse> getMyNotifications(Authentication authentication) {
        return service.getMyNotifications(authentication);
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @Operation(summary = "Get by id notification!", description = "This method for getting by id notification!")
    @GetMapping("/{notificationId}")
    public NotificationResponse getNotificationById(@PathVariable Long notificationId){
        return service.getById(notificationId);
    }
}
