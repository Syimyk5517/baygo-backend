package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.NotificationResponse;
import com.example.baygo.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Buyer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerNotificationController {
    private final NotificationService service;

    @PreAuthorize("hasAuthority('BUYER')")
    @Operation(summary = "Get all", description = "This method for gets all buyer notifications")
    @GetMapping
    public List<NotificationResponse> getBuyerNotifications() {
        return service.getBuyerNotifications();
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @Operation(summary = "Get by supplyId notification!", description = "This method for getting by supplyId notification!")
    @GetMapping("/{notificationId}")
    public NotificationResponse getNotificationById(@PathVariable Long notificationId){
        return service.getById(notificationId);
    }
}
