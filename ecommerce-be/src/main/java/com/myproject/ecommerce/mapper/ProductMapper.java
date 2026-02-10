package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ProductDetailResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest productRequest);
    ProductSummaryResponse toProductSummaryResponse(Product product);
    ProductDetailResponse toProductDetailResponse(Product product);
    void update(@MappingTarget Product product, ProductRequest productRequest);
}
