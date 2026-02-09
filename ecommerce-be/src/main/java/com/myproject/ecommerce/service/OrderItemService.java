package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.OrderItemRequest;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.OrderItem;
import com.myproject.ecommerce.mapper.OrderItemMapper;
import com.myproject.ecommerce.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<OrderItemResponse> getActiveOrderItems(Long accountId){
        return orderItemRepository.findActiveOrderItemsByAccountId(accountId);
    }

    @Transactional(readOnly = true)
    public List<OrderItemResponse> getOrderItemsHistory(Long accountId){
        return orderItemRepository.getOrderItemsHistory(accountId);
    }
}
