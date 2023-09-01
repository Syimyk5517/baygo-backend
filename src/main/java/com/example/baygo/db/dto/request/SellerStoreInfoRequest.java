package com.example.baygo.db.dto.request;

import com.example.baygo.validations.BicValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SellerStoreInfoRequest {
    @NotBlank(message = "Имя магазина не должен быть пустым")
    private String nameOfStore;
    @NotBlank(message = "Адрес не должен быть пустым")
    private String storeAddress;
    @NotBlank(message = "ИНН не должен быть пустым")
    @Pattern(regexp = "\\d{10}|\\d{14}", message = "Некоррекный  INN")
    private String ITN;
    @NotBlank(message = "Расчетный счет не должен быть пустым")
    private int checkingCheck;
    @NotBlank(message = "BIC не должен быть пустым")
    @BicValid(message = "Некоррекный BIC")
    private String BIC;
    @NotBlank(message = "Описание о магазина не должен быть пустым")
    private String aboutStore;
}
