package com.example.baygo.db.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProductDTO {
        @NotNull(message = " должна быть указана!!!")
        private Long productId;
        @NotNull(message = "Под категория должна быть указана!!!")
        private Long subCategoryId;
        @NotBlank(message = "Страна производства должна быть указана!!!")
        private String manufacturer;
        @NotBlank(message = "Бренд должен быть указан!!!")
        private String brand;
        @NotBlank(message = "Название продукта должно быть указано!!!")
        private String name;
        @NotBlank(message = "Сезон должен быть указан!!!")
        private String season;
        @NotBlank(message = "Состав должен быть указан!!!")
        private String composition;
        @Valid
        @NotEmpty(message = "Под продукты не должны быть пустыми!!!")
        private List<UpdateSubProductDTO> subProducts;

        public UpdateProductDTO(Long productId, Long subCategoryId, String manufacturer, String brand, String name, String season, String composition) {
                this.productId = productId;
                this.subCategoryId = subCategoryId;
                this.manufacturer = manufacturer;
                this.brand = brand;
                this.name = name;
                this.season = season;
                this.composition = composition;
        }
}
