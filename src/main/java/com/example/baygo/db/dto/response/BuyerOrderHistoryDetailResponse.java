package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerOrderHistoryDetailResponse {
    private String dateOfOrder;
    private String orderNumber;
    private boolean withDelivery;
    private BigDecimal beforeDiscountPrice;
    private BigDecimal discountPrice;
    private BigDecimal afterDiscountPrice;
    private List<BuyerOrderProductsResponse> products;

    public void addToProduct(List<BuyerOrderProductsResponse> product) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.addAll(product);
    }

    public BuyerOrderHistoryDetailResponse(String dateOfOrder, String orderNumber, boolean withDelivery, BigDecimal beforeDiscountPrice, BigDecimal discountPrice, BigDecimal afterDiscountPrice) {
        this.dateOfOrder = dateOfOrder;
        this.orderNumber = orderNumber;
        this.withDelivery = withDelivery;
        this.beforeDiscountPrice = beforeDiscountPrice;
        this.discountPrice = discountPrice;
        this.afterDiscountPrice = afterDiscountPrice;
    }

}
