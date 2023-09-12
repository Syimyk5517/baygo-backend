package com.example.baygo.db.dto.request.order;

import com.example.baygo.validations.NameValid;
import com.example.baygo.validations.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CustomerInfoRequest(
        @NotBlank(message = "Необходимо указать имя.")
        @NameValid(message = "Имя должно содержать от 2 до 40 символов.")
        String firsName,
        @NotBlank(message = "Необходимо указать фамилию.")
        @NameValid(message = "фамилия должно содержать от 2 до 40 символов.")
        String lastName,
        @NotBlank(message = "Почта не должна быть пустой")
        @Email(message = "Напишите действительный адрес электронной почты!")
        String email,
        @NotBlank(message = "Номер телефона не должен быть пустым")
        @PhoneNumberValid(message = "Номер телефона должен начинаться с +996, состоять из 13 символов и должен быть действительным!")
        String phoneNumber,
        @NotBlank(message = "Необходимо указать страну.")
        String country,
        @NotBlank(message = "Необходимо указать город.")
        String city,
        @NotBlank(message = "Необходимо указать почтовый индекс.")
        String postalCode,
        @NotBlank(message = "Адрес не должен быть пустым")
        String address
) {
}
