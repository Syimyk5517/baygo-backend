package com.example.baygo.db.custom;

import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;

import java.time.LocalDate;
import java.util.List;

public interface CustomDeliveryFactor {
    List<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page);

}
