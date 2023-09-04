package com.example.baygo.db.dto.response.supply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDraftResponse {
    private Long supplyId;
    private LocalDate createdAt;
    private LocalDate changedAt;
    private int numberOfArticlesPcs;
    private int numberOfProductsPcs;
    private String phoneNumber;
    private String supplyNumber;

}
