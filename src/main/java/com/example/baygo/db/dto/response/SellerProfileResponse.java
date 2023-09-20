package com.example.baygo.db.dto.response;

import lombok.Builder;

@Builder
public record SellerProfileResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String nameOfStore,
        String storeAddress,
        String ITN,
        String storeLogo,
        int checkingCheck,
        String BIC,
        String aboutStore
) {
}
