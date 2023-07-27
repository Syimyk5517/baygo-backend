package com.example.baygo.db.dto.request;

import com.example.baygo.db.model.enums.SupplyType;
import com.example.baygo.db.validations.NameValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SupplierRequest (
    @NotBlank(message = "Необходимо указать имя.")
    @NameValid(message = "Имя должно содержать от 2 до 40 символов.")
    String name,
    @NotBlank(message = "Необходимо указать фамилию.")
    @NameValid(message = "фамилия должно содержать от 2 до 40 символов.")
    String surname,
    @NotBlank(message = "Необходимо указать марка автомобиля")
    @NameValid(message = "марка автомобиля должно содержать от 2 до 40 символов.")
    String carBrand,
    @NotBlank(message = "Необходимо указать номер автомобиля.")
    @NameValid(message = "номер автомобиля должно содержать от 2 до 40 символов.")
    String carNumber,
    SupplyType supplyType
){
}
