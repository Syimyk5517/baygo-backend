package com.example.baygo.db.dto.request;

import java.time.LocalDate;

public record DeliveryFactorRequest(
        String keyWord,
        LocalDate date
) {
}
