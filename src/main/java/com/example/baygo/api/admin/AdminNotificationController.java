package com.example.baygo.api.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.baygo.db.dto.request.NotificationSendRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Admin API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminNotificationController {
    private final NotificationService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Send notification!", description = "This method for sending notification!")
    @PostMapping
    public SimpleResponse sendNotificationToBuyers(@RequestBody NotificationSendRequest notificationSendRequest){
        return service.sendNotificationToBuyer(notificationSendRequest);
    }

}
