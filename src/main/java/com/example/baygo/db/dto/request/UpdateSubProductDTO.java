package com.example.baygo.db.dto.request;

import com.example.baygo.validations.ImageUrlValid;
import com.example.baygo.validations.ImageUrlsValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSubProductDTO {
        @NotNull(message = "Под продукт должна быть указана!")
        private Long subProductId;
        @NotBlank(message = "Код цвета должен быть указан!")
        private String colorHexCode;
        @NotBlank(message = "Цвет должен быть указан!")
        private String color;
        @NotNull(message = "Цена должна быть указана!")
        @Min(value = 0, message = "Цена должна быть положительным числом!")
        private BigDecimal price;
        @NotEmpty(message = "Изображение должно быть указано!")
        @ImageUrlValid
        private String mainImage;
        @NotEmpty(message = "Изображения должны быть указаны!")
        @ImageUrlsValid
        private List<String> images;
        @NotBlank(message = "Характеристика должна быть указана!")
        private String description;
        @NotBlank(message = "Артикул продавца должен быть указан!")
        private String articulOfSeller;
        @Positive(message = "Высота должен быть  положительным!")
        private int height;
        @Positive(message = "Ширина должна быть положительным!")
        private int width;
        @Positive(message = "Длина должна быть положительным!")
        private int length;
        @Positive(message = "Вес должен быть положительным!")
        private double weight;
        @Valid
        @NotEmpty(message = "Размеры не должны быть пустыми!")
        private List<UpdateSizeDTO>sizes;

        public UpdateSubProductDTO(Long subProductId, String colorHexCode, String color, BigDecimal price, String mainImage, String description, String articulOfSeller, int height, int width, int length, double weight) {
                this.subProductId = subProductId;
                this.colorHexCode = colorHexCode;
                this.color = color;
                this.price = price;
                this.mainImage = mainImage;
                this.description = description;
                this.articulOfSeller = articulOfSeller;
                this.height = height;
                this.width = width;
                this.length = length;
                this.weight = weight;
        }
}
