package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.OrderRequest;
import com.myproject.ecommerce.dto.response.OrderResponse;
import com.myproject.ecommerce.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {
    OrderEntity toEntity(OrderRequest orderRequest);
    OrderResponse toResponse(OrderEntity orderEntity);
    void update(@MappingTarget OrderEntity orderEntity, OrderRequest orderRequest);
}
