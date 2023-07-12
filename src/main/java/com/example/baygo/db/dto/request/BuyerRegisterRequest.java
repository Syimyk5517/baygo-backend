package com.example.baygo.db.dto.request;

import com.example.baygo.db.validations.NameValid;
import com.example.baygo.db.validations.PasswordValid;
import com.example.baygo.db.validations.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record BuyerRegisterRequest(
        @NotBlank(message = "Необходимо указать имя.")
        @NameValid(message = "Имя должно содержать от 2 до 33 символов.")
        String firstName,
        @NotBlank(message = "Необходимо указать фамилию.")
        @NameValid(message = "Фамилия должна содержать от 2 до 33 символов.")
        String lastName,
        @NotBlank(message = "Номер телефона не должен быть пустым")
        @PhoneNumberValid(message = "Номер телефона должен начинаться с +996, состоять из 13 символов и должен быть действительным!")
        String phoneNumber,
        @NotBlank(message = "Почта не должна быть пустой")
        @Email(message = "Напишите действительный адрес электронной почты!")
        String email,
        @NotBlank(message = "Пароль не должен быть пустым")
        @PasswordValid(message = "Длина пароля должна быть более 8 символов и содержать как минимум одну заглавную букву!")
        String password,
        LocalDate dateOfBirth,
        @NotBlank(message = "Пол должен быть \"Мужчина\" или \"Женшина\"")
        String gender,
        @NotBlank(message = "Адрес не должен быть пустым")
        String address
) {
}
