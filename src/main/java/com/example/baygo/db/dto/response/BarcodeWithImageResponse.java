package com.example.baygo.db.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeWithImageResponse {
    String barcode;
    String image;
}
