package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.OrderItemRequest;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.OrderItem;
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
        OrderItem orderItem = orderItemMapper.toEntity(orderItemRequest);
        return orderItemMapper.toResponse(orderItemRepository.save(orderItem));
    }

    @Transactional(readOnly = true)
    public OrderItemResponse getOrderItem(Long id){
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item doesn't exist"));

        return orderItemMapper.toResponse(orderItem);
    }

    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest orderItemRequest){
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item doesn't exist"));

        orderItemMapper.update(orderItem, orderItemRequest);
        return orderItemMapper.toResponse(orderItem);
    }
}
