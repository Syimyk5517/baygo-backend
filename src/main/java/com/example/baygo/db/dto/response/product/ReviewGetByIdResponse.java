package com.example.baygo.db.dto.response.product;

import lombok.Builder;

import java.util.List;
@Builder
public record ReviewGetByIdResponse(
        double rating,
        List<RatingResponse> ratings,
        List<ImagesResponse>images,
        List<ReviewForProduct>review
) {
}
