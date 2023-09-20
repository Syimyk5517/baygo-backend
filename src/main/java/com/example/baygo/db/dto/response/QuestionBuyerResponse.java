package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record QuestionBuyerResponse(
    Long id,
    String buyerAvatar,
    String buyerName,
    String questionDate,
    String question,
    String answer,
    String answerDate
) {
}
