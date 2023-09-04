package com.example.baygo.db.dto.request;

import lombok.Builder;

@Builder
public record NotificationRequest(String tittle,
                                  String message) {
}
