package com.example.baygo.db.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class BuyerCategoryResponse{
        private Long categoryId;
        private String categoryName;
        private List<SubCategoryResponse> subcategories;
}
