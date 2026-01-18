package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.ReviewRequest;
import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewEntityMapper {
    ReviewEntity toEntity(ReviewRequest reviewRequest);
    ReviewResponse toResponse(ReviewEntity reviewEntity);
    void update(@MappingTarget ReviewEntity reviewEntity, ReviewRequest reviewRequest);
}
