package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record QRCodeWithImageResponse(
        String qrCode,
        String qrCodeImage
) {
}
