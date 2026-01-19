package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.ProductImageRequest;
import com.myproject.ecommerce.dto.response.ProductImageResponse;
import com.myproject.ecommerce.entity.ProductImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImageEntity toEntity(ProductImageRequest productImageRequest);
    ProductImageResponse toResponse(ProductImageEntity productImageEntity);
    void update (@MappingTarget ProductImageEntity productImageEntity, ProductImageRequest productImageRequest);
}
