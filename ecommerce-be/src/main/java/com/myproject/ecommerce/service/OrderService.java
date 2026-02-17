package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.response.OrderDetailResponse;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.Order;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.mapper.OrderMapper;
import com.myproject.ecommerce.repository.OrderItemRepository;
import com.myproject.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;


    // get my order detail
    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(Long accountId, Long orderId){

        Order order = orderRepository.findOrderByIdAndAccountId(accountId, orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));

        OrderDetailResponse response = orderMapper.toDetailResponse(order);

        List<OrderItemResponse> orderItemResponses = orderItemRepository.getOrderItemsforFeedback(order.getId());
        response.setOrderItemResponseList(orderItemResponses);

        return response;
    }

}
