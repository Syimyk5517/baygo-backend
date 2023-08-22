package com.example.baygo.db.dto.response.fbs;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetSupplyWithOrders {
    private Long id;
    private String name;
    private LocalDateTime createAt;
    private int totalQuantity;
    private String QRCode;
    private List<GetAllFbsOrderBySupplyId> orders;

    public GetSupplyWithOrders(Long id, String name, LocalDateTime createAt, int totalQuantity, String QRCode) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.totalQuantity = totalQuantity;
        this.QRCode = QRCode;
    }

}
