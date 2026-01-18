package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.OrderItemRequest;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemEntityMapper {
    OrderItemEntity toEntity(OrderItemRequest orderItemRequest);
    OrderItemResponse toResponse(OrderItemEntity orderItemEntity);
    void update(@MappingTarget OrderItemEntity orderItemEntity, OrderItemRequest orderItemRequest);
}
