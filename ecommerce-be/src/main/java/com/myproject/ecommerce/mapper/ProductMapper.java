package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ProductResponse;
import com.myproject.ecommerce.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductRequest productRequest);
    ProductResponse toResponse(ProductEntity productEntity);
    void update(@MappingTarget ProductEntity productEntity, ProductRequest productRequest);
}
