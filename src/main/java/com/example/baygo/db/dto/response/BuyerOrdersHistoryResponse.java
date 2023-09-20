package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerOrdersHistoryResponse {
    private Long orderId;
    private String dateOfOrder;
    private String orderNumber;
    private Long quantityOfProducts;
    private BigDecimal totalPrice;
}
