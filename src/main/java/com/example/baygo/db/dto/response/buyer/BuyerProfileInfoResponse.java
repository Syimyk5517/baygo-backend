package com.example.baygo.db.dto.response.buyer;

public record BuyerProfileInfoResponse(
        Long id,
        String image,
        String fullName,
        String email,
        String phoneNumber
) {
}
