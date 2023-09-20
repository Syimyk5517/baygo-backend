package com.example.baygo.db.dto.request.seller;

import com.example.baygo.db.model.enums.AdvertisementPlace;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AdvertisementSaveRequest(

        @NotBlank(message = "Бренд не должен быть пустым!!!")
        String brand,
        @NotNull(message = "Категория должна быть указана!!!")
        Long categoryId,
        AdvertisementPlace advertisementPlace,
        @FutureOrPresent(message = "Дата начала акции должна быть в будущем или сегодня!")
        LocalDate startPromotion,

        @Future(message = "Дата окончания акции должна быть в будущем!")
        LocalDate endPromotion,

        @Positive(message = "Бюджет компании должен быть положительным числом!")
        BigDecimal companyBudget,

        @PositiveOrZero(message = "Стоимость за миллениум должна быть неотрицательным числом!")
        double costPerMillennium,

        @Min(value = 0, message = "Прогноз отображения рекламы должен быть не менее 0")
        int displayForecast,
        Boolean isDay,
        Boolean isNew,
        @NotBlank(message = "Фото не должно быть пустым")
        String photo,
        @NotNull(message = "Выберите продукт для ссылки")
        List<Long> subProductId
) {
}
