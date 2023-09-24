package com.example.baygo.db.dto.request;

import jakarta.validation.constraints.Positive;

public record ProductDimensionsRequest(
        @Positive(message = "Width should by positive number")
        double width,
        @Positive(message = "Length should by positive number")
        double length,
        @Positive(message = "Height should by positive number")
        double height,
        @Positive(message = "Weight should by positive number")
        double weight,
        @Positive(message = "Amount should by positive number")
        int amount
) {
}
