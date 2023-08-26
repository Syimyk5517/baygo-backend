package com.example.baygo.db.dto.request.fbs;

import com.example.baygo.validations.NameValid;
import com.example.baygo.validations.PhoneNumberValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SupplyAccessCardRequest(
        Long warehouseId,
        @NotNull(message = "Необходимо указать имя.")
        @NameValid(message = "Имя должно содержать от 2 до 40 символов.")
        String driverFirstName,
        @NotNull(message = "Необходимо указать фамилию.")
        @NameValid(message = "фамилия должно содержать от 2 до 40 символов.")
        String driverLastname,
        @NotNull(message = "Необходимо указать марка автомобиля")
        @NameValid(message = "марка автомобиля должно содержать от 2 до 40 символов.")
        String brand,

        @NotBlank(message = "Номер телефона не должен быть пустым")
        @PhoneNumberValid(message = "Номер телефона должен начинаться с +996, состоять из 13 символов и должен быть действительным!")
        String number
) {
}
