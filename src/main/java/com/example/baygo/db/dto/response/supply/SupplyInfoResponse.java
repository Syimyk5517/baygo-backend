package com.example.baygo.db.dto.response.supply;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SupplyInfoResponse {
    private Long supplyId;
    private Long productPackageId;
    private String packageBarcode;
    private String packageBarcodeImage;
    private List<NumberOfProductResponse> numberOfProductResponses;
}
