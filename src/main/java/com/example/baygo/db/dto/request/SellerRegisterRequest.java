package com.example.baygo.db.dto.request;

import com.example.baygo.validations.BicValid;
import com.example.baygo.validations.NameValid;
import com.example.baygo.validations.PasswordValid;
import com.example.baygo.validations.PhoneNumberValid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record SellerRegisterRequest(
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
        @Past(message = "Дата должна быть в прошедшем времени!!")
        @NotNull(message = "Дата выпуска должна быть указана!!!")
        LocalDate dateOfBirth,
        @NotBlank(message = "Адрес не должен быть пустым")
        String address,
        @NotBlank(message = "Имя магазина не должен быть пустым")
        String nameOfStore,
        @NotBlank(message = "ИНН не должен быть пустым")
        @Pattern(regexp = "\\d{10}|\\d{14}", message = "Некоррекный  INN")
        String ITN,
        @NotBlank(message = "BIC не должен быть пустым")
        @BicValid(message = "Некоррекный BIC")
        String BIC
) {
}
