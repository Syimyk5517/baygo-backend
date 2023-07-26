package com.example.baygo.db.dto.request;

import com.example.baygo.db.validations.NameValid;
import com.example.baygo.db.validations.PasswordValid;
import com.example.baygo.db.validations.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record BuyerRegisterRequest(
        @NotBlank(message = "Необходимо указать имя и фамилию.")
        @NameValid(message = "Имя и фамилия должно содержать от 2 до 40 символов.")
        String fullName,
        @NotBlank(message = "Почта не должна быть пустой")
        @Email(message = "Напишите действительный адрес электронной почты!")
        String email,
        @NotBlank(message = "Пароль не должен быть пустым")
        @PasswordValid(message = "Длина пароля должна быть более 6 символов и содержать как минимум одну заглавную букву!")
        String password
) {
}
