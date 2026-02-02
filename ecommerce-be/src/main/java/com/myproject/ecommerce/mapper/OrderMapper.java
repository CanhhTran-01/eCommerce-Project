package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.OrderRequest;
import com.myproject.ecommerce.dto.response.OrderResponse;
import com.myproject.ecommerce.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);
    OrderResponse toResponse(Order order);
    void update(@MappingTarget Order order, OrderRequest orderRequest);
}
