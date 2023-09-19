package com.example.baygo.db.dto.request.seller;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AdvertisementSaveRequest(

        @NotBlank(message = "Бренд не должен быть пустым!!!")
        String brand,
        @NotBlank(message = "Почта не должна быть пустой")
        @Email(message = "Напишите действительный адрес электронной почты!")
        String email,
        @NotNull(message = "Категория должна быть указана!!!")
        Long categoryId,
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

        @NotBlank(message = "Фото не должно быть пустым")
        String photo,

        @NotBlank(message = "URL не должен быть пустым")
        String url
) {
}
