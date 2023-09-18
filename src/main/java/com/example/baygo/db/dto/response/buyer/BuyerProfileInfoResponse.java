package com.example.baygo.db.dto.response.buyer;

import com.example.baygo.db.model.enums.Gender;

import java.time.LocalDate;

public record BuyerProfileInfoResponse(
        String image,
        String fullName,
        LocalDate dateOfBirth,
        Gender gender,
        String address,
        String email,
        String phoneNumber
) {
}
