package com.example.baygo.db.dto.request.excel;

import com.poiji.annotation.ExcelCellName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductInfoExcelRequest(
        @ExcelCellName("Штрих-код продукта")
        @NotBlank(message = "Необходимо указать баркод продукт.")
        @NotNull(message = "Необходимо указать баркод продукт.")
        String barcodeProduct,
        @ExcelCellName("Количество продукта")
        @Min(value = 1, message = "Количество должно быть больше нуля")
        int quantityProduct,
        @ExcelCellName("Штрих-код короба")
        @NotBlank(message = "Необходимо указать баркод коробку.")
        @NotNull(message = "Необходимо указать баркод коробку.")
        String barcodeBox
) {
}
