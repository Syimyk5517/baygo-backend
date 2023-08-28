package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.NotificationResponse;
import com.example.baygo.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerNotificationApi {
    private final NotificationService service;

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
