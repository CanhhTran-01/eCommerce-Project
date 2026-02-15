package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.OrderRequest;
import com.myproject.ecommerce.dto.response.OrderDetailResponse;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.Order;
import com.myproject.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);
    OrderDetailResponse toDetailResponse(Order order);
    OrderItemResponse toItemResponse(OrderItem orderItem);
    void update(@MappingTarget Order order, OrderRequest orderRequest);
}
