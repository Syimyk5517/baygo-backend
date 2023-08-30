package com.example.baygo.db.dto.response.buyer;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReturnGetByIdResponse {
    private Long id;
    private int barcode;
    private String mainImage;
   private String productName;
   private String size;
   private int quantity;
   private String reason;
   private OrderStatus orderStatus;
   private List<String> images;

    public ReturnGetByIdResponse(Long id, int barcode, String mainImage, String productName, String size, int quantity, String reason, OrderStatus orderStatus) {
        this.id = id;
        this.barcode = barcode;
        this.mainImage = mainImage;
        this.productName = productName;
        this.size = size;
        this.quantity = quantity;
        this.reason = reason;
        this.orderStatus = orderStatus;
    }
}
