package com.example.baygo.db.dto.request;

import com.example.baygo.db.model.Buyer;
import lombok.Builder;

@Builder
public record NotificationRequest(String tittle,
                                  String message) {
}
