package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.ReviewRequest;
import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewRequest reviewRequest);
    ReviewResponse toResponse(Review review);
    void update(@MappingTarget Review review, ReviewRequest reviewRequest);
}
