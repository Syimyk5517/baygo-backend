package com.example.baygo.db.dto.response.buyer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AppealResponse(
        @NotEmpty(message = "Title is required") String title,
        @NotEmpty(message = "Divide is required") String divide,
        @NotEmpty(message = "Detail of Appeal is required") String detailOfAppeal
) {
}
