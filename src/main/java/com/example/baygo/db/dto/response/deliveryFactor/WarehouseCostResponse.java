package com.example.baygo.db.dto.response.deliveryFactor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseCostResponse{
       private LocalDate date;
       private String plat;
       private BigDecimal warehouseCost;



}
