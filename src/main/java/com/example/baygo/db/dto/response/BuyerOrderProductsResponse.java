package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerOrderProductsResponse{
        private Long sizeId;
        private int quantityOfSize;
        private OrderStatus orderStatus;
        private LocalDateTime dateOfReceived;
        private String QRCode;


}
