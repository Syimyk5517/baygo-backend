package com.example.baygo.db.dto.response.buyer;

import lombok.Builder;

@Builder
public record BuyerProfileInfoResponse(
        Long id,
        String fullName,
        String email,
        String phoneNumber
) {
}
