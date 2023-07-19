package com.example.baygo.db.responses;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record SimpleResponse(
        String message,
        HttpStatus status
) {
}
