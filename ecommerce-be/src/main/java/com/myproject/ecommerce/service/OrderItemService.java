package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.OrderItemRequest;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.OrderItemEntity;
import com.myproject.ecommerce.mapper.OrderItemEntityMapper;
import com.myproject.ecommerce.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemEntityMapper orderItemEntityMapper;

    public OrderItemResponse createOrderItem(OrderItemRequest orderItemRequest){
        OrderItemEntity orderItemEntity = orderItemEntityMapper.toEntity(orderItemRequest);
        return orderItemEntityMapper.toResponse(orderItemRepository.save(orderItemEntity));
    }

    @Transactional(readOnly = true)
    public OrderItemResponse getOrderItem(Long id){
        OrderItemEntity orderItemEntity = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item doesn't exist"));

        return orderItemEntityMapper.toResponse(orderItemEntity);
    }

    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest orderItemRequest){
        OrderItemEntity orderItemEntity = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Item doesn't exist"));

        orderItemEntityMapper.update(orderItemEntity, orderItemRequest);
        return orderItemEntityMapper.toResponse(orderItemEntity);
    }
}
