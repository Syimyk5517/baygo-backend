package com.example.baygo.db.dto.request;
import lombok.Builder;

import java.util.List;

@Builder
public record PassSurveyRequest(
        List<Long> optionIdes,
        String fullName,
        String phoneNumber,
        String suggestionOrComments,
        int rating
) {
}
