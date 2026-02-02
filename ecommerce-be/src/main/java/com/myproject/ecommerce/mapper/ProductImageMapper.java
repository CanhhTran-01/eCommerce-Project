package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.ProductImageRequest;
import com.myproject.ecommerce.dto.response.ProductImageResponse;
import com.myproject.ecommerce.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImage toEntity(ProductImageRequest productImageRequest);
    ProductImageResponse toResponse(ProductImage productImage);
    void update (@MappingTarget ProductImage productImage, ProductImageRequest productImageRequest);
}
