package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.CategoryRequest;
import com.myproject.ecommerce.dto.response.CategoryResponse;
import com.myproject.ecommerce.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
}
