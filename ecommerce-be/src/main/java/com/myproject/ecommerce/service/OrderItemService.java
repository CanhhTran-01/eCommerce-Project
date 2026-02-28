package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.mapper.OrderItemMapper;
import com.myproject.ecommerce.repository.OrderItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    // get active order items (Incomplete)
    @Transactional(readOnly = true)
    public List<OrderItemResponse> getActiveOrderItems(Long accountId) {
        return orderItemRepository.findActiveOrderItemsByAccountId(accountId);
    }

    // get order items history
    @Transactional(readOnly = true)
    public List<OrderItemResponse> getOrderItemsHistory(Long accountId) {
        return orderItemRepository.getOrderItemsHistory(accountId);
    }
}
