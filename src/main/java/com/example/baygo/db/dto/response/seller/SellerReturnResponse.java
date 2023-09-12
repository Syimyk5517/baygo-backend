package com.example.baygo.db.dto.response.seller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerReturnResponse {
    private Long id;
    private int barcode;
    private String mainImage;
    private String productName;
    private String size;
    private int quantity;


}
