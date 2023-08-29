package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.Size;
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
public class BuyerOrdersHistoryResponse {
    private Long orderId;
    private LocalDateTime dateOfOrder;
    private String orderNumber;
    private int quantityOfProducts;
    private BigDecimal totalPrice;
    private List<BuyerOrderProductsResponse> products;
    public void addToProduct(List<BuyerOrderProductsResponse> product) {
        if (this.products == null){
            this.products = new ArrayList<>();
        }
        this.products.addAll(product);
    }

    public BuyerOrdersHistoryResponse(Long orderId, LocalDateTime dateOfOrder, String orderNumber, int quantityOfProducts, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.dateOfOrder = dateOfOrder;
        this.orderNumber = orderNumber;
        this.quantityOfProducts = quantityOfProducts;
        this.totalPrice = totalPrice;
    }
}
