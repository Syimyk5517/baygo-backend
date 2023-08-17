package com.example.baygo.db.dto.request;

import lombok.Builder;

@Builder
public record QuestionOfSellerUpdateRequest(Long id,
                                            String answer) {
}
