package com.example.baygo.db.dto.request;

import com.example.baygo.db.model.enums.Gender;
import com.example.baygo.validations.PasswordValid;
import com.example.baygo.validations.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record BuyerProfileRequest(
        @NotBlank(message = "Необходимо указать имя.")
        @Size(min = 2, max = 50, message = "Ф.И.О должно содержать от 2 до 50 символов.")
        String fullName,
        LocalDate dateOfBirth,
        Gender gender,
        @NotBlank(message = "Адрес не должен быть пустым")
        String region,
        @NotBlank(message = "Почта не должна быть пустой")
        @Email(message = "Напишите действительный адрес электронной почты!")
        String email,
        @NotBlank(message = "Номер телефона не должен быть пустым")
        @PhoneNumberValid(message = "Номер телефона должен начинаться с +996, состоять из 13 символов и должен быть действительным!")
        String phoneNumber

) {
}
