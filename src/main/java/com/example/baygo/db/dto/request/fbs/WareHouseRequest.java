package com.example.baygo.db.dto.request.fbs;

import com.example.baygo.db.model.enums.DayOfWeek;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
@Builder
public record WareHouseRequest(
        @NotBlank(message = "Название склада не может быть пустым")
        String wareHouseName,
        @NotBlank(message = "Страна не может быть пустой")
        String country,
        @NotBlank(message = "Город не может быть пустым")
        String city,
        @NotBlank(message = "Улица не может быть пустой")
        String street,
        @Min(value = 1, message = "Индекс страны должен быть больше нуля")
        int indexOfCountry,
        @Min(value = 1, message = "Номер дома должен быть больше нуля")
        int houseNumber,
        @NotBlank(message = "Номер телефона не может быть пустым")
        String phoneNumber,
        @NotEmpty(message = "Дни недели не могут быть пустыми")
        List<DayOfWeek> dayOfWeek,
        @Min(value = 1, message = "Время подготовки поставки должно быть больше нуля")
        int preparingSupply,
        @Min(value = 1, message = "Время сборки должно быть больше нуля")
        int assemblyTime,
        @NotNull(message = "Идентификатор склада не может быть пустым")
        Long wareHouseId
) {
}
