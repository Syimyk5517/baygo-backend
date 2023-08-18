package com.example.baygo.db.dto.response.fbs;

import lombok.Data;


import java.util.List;
@Data
public class GetSupplyWithOrders {
    private List<GetByIDFbsSupplyResponse>supply;
    private List<GetAllFbsOrderBySupplyId>orders;
}
