package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record OrderWareHouseResponse(
        String wareHouseName,
        int percentage

) {
}
