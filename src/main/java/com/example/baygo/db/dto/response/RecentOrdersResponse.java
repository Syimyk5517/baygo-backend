package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.Size;
import com.example.baygo.db.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
public class RecentOrdersResponse {
    private Long orderId;
    private String articul;
    private String productName;
    private BigDecimal price;
    private Map<Size,Integer> productCount;
    private Status status;

    public RecentOrdersResponse(Long orderId, String articul, String productName, BigDecimal price, Map<Size, Integer> productCount, Status status) {
        this.orderId = orderId;
        this.articul = articul;
        this.productName = productName;
        this.price = price;
        this.productCount = productCount;
        this.status = status;
    }
}
