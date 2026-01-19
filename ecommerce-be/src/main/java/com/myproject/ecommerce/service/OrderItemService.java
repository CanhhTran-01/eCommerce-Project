package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.OrderItemRequest;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.OrderItemEntity;
import com.myproject.ecommerce.mapper.OrderItemMapper;
import com.myproject.ecommerce.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemResponse createOrderItem(OrderItemRequest orderItemRequest){
        OrderItemEntity orderItemEntity = orderItemMapper.toEntity(orderItemRequest);
        return orderItemMapper.toResponse(orderItemRepository.save(orderItemEntity));
    }

    @Transactional(readOnly = true)
    public OrderItemResponse getOrderItem(Long id){
        OrderItemEntity orderItemEntity = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item doesn't exist"));

        return orderItemMapper.toResponse(orderItemEntity);
    }

    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest orderItemRequest){
        OrderItemEntity orderItemEntity = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item doesn't exist"));

        orderItemMapper.update(orderItemEntity, orderItemRequest);
        return orderItemMapper.toResponse(orderItemEntity);
    }
}
