package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.response.ThumbnailImageResponse;
import com.myproject.ecommerce.entity.ProductThumbnailImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductGalleryMapper {
    ThumbnailImageResponse toThumbnailImageResponse(ProductThumbnailImage image);
}
