package com.example.baygo.db.dto.response.admin;

import java.time.LocalDateTime;

public record AdminFBSSuppliesResponse(
        Long supplyId,
        String qrCode,
        LocalDateTime createAt,
        Long quantity,
        LocalDateTime receivedAt,
        String fullName

) {
}
