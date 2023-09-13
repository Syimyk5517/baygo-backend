package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerOrderProductsResponse{
        private Long sizeId;
        private int quantityOfSize;
        private OrderStatus orderStatus;
        private String dateOfReceived;
        private String QRCode;
        private int percentOfDiscount;
        private BigDecimal price;
        private String mainImage;
        private String name;
        private String size;
}
