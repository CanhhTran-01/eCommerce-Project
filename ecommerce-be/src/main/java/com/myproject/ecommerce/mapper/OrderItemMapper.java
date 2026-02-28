package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.OrderItemRequest;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemRequest orderItemRequest);

    OrderItemResponse toResponse(OrderItem orderItem);

    void update(@MappingTarget OrderItem orderItem, OrderItemRequest orderItemRequest);
}
