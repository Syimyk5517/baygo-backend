package com.example.baygo.db.dto.request.fbs;

import com.example.baygo.db.model.enums.DayOfWeek;
import lombok.Builder;

import java.util.List;
@Builder
public record WareHouseRequest(
        String wareHouseName,

        String country,
        String city,
        String street,
        int indexOfCountry,
        int houseNumber,
        String phoneNumber,

        List<DayOfWeek> dayOfWeek,
        int preparingSupply,
        int assemblyTime,
        Long wareHouseId
) {
}
