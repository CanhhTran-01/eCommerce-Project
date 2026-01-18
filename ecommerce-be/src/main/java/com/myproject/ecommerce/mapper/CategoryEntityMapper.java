package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.CategoryRequest;
import com.myproject.ecommerce.dto.response.CategoryResponse;
import com.myproject.ecommerce.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    CategoryEntity toEntity(CategoryRequest categoryRequest);
    CategoryResponse toResponse(CategoryEntity categoryEntity);
    void updateCategory(@MappingTarget CategoryEntity categoryEntity, CategoryRequest categoryRequest);
}
