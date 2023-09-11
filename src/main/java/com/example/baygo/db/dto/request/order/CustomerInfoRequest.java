package com.example.baygo.db.dto.request.order;

import lombok.Builder;

@Builder
public record CustomerInfoRequest(
        String firsName,
        String lastName,
        String email,
        String phoneNumber,
        String country,
        String city,
        String postalCode,
        String address
) {
}
