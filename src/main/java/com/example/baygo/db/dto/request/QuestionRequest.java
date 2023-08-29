package com.example.baygo.db.dto.request;
import java.util.List;

public record QuestionRequest(
        int questionOrder,
        String title,
        List<OptionRequest> optionRequests
) {
}