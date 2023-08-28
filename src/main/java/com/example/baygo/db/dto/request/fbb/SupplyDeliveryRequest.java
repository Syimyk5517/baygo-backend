package com.example.baygo.db.dto.request.fbb;

import com.example.baygo.db.model.enums.SupplyType;
import com.example.baygo.validations.NameValid;
import com.example.baygo.validations.PhoneNumberValid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SupplyDeliveryRequest(
        @NotBlank(message = "Пропуск для доставки не может быть пустым")
        @NotNull(message = "Пропуск не может быть пустым")
        String deliveryPass,
        @NotBlank(message = "Необходимо указать имя.")
        @NameValid(message = "Имя должно содержать от 2 до 40 символов.")
        String driverName,
        @NotBlank(message = "Необходимо указать фамилию.")
        @NameValid(message = "фамилия должно содержать от 2 до 40 символов.")
        String driverSurname,
        @NotBlank(message = "Необходимо указать марка автомобиля")
        @NameValid(message = "марка автомобиля должно содержать от 2 до 40 символов.")
        String carBrand,

        @NotBlank(message = "Номер телефона не должен быть пустым")
        @PhoneNumberValid(message = "Номер телефона должен начинаться с +996, состоять из 13 символов и должен быть действительным!")
        String carNumber,
        @NotNull(message = "Тип упаковки не может быть пустым")
        SupplyType supplyType,
        @Min(value = 1, message = "Количество мест должно быть больше нуля")
        int numberOfSeats
) {
}
