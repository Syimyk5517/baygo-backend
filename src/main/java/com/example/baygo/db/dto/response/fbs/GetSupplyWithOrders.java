package com.example.baygo.db.dto.response.fbs;

import com.example.baygo.db.model.enums.FBSSupplyStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GetSupplyWithOrders {
    private Long supplyId;
    private String name;
    private LocalDate createAt;
    private Long totalQuantity;
    private FBSSupplyStatus status;
    private String QRCode;
    private List<GetAllFbsOrderBySupplyId> orders;

    public GetSupplyWithOrders(Long supplyId, String name, LocalDate createAt, Long totalQuantity, FBSSupplyStatus status, String QRCode) {
        this.supplyId = supplyId;
        this.name = name;
        this.createAt = createAt;
        this.totalQuantity = totalQuantity;
        this.status = status;
        this.QRCode = QRCode;
    }

}
