package com.example.baygo.db.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;
@Builder
public record SimpleResponse(
        HttpStatus httpStatus,
        String message) {
}
