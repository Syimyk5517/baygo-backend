package com.example.baygo.db.dto.request;

import com.example.baygo.db.model.Seller;
import com.example.baygo.db.validations.NameValid;
import com.example.baygo.db.validations.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SellerProfileRequest {
    @NotBlank(message = "Необходимо указать имя.")
    @NameValid(message = "Имя должно содержать от 2 до 33 символов.")
    private String firstName;
    @NotBlank(message = "Необходимо указать фамилию.")
    @NameValid(message = "Фамилия должна содержать от 2 до 33 символов.")
    private String lastName;
    @NotBlank(message = "Почта не должна быть пустой")
    @Email(message = "Напишите действительный адрес электронной почты!")
    private String email;
    @NotBlank(message = "Номер телефона не должен быть пустым")
    @PhoneNumberValid(message = "Номер телефона должен начинаться с +996, состоять из 13 символов и должен быть действительным!")
    private String phoneNumber;
    @NotBlank(message = "Адрес не должен быть пустым")
    private String address;
}
