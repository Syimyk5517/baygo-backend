package com.example.baygo.db.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SellerStoreInfoRequest {
    private String photo;
    private String nameOfStore;
    private String storeEmail;
    private String storeAddress;
    private String ITN;
    private String storeLogo;
    private int checkingCheck;
    private String BIC;
    private String aboutStore;
}
