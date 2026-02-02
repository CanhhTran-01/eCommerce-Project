package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.OrderRequest;
import com.myproject.ecommerce.dto.response.OrderResponse;
import com.myproject.ecommerce.entity.Order;
import com.myproject.ecommerce.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;

    public OrderResponse createOrder(OrderRequest orderRequest){
        Order order = orderMapper.toEntity(orderRequest);

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrderList(){
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    public OrderResponse updateOrder(Long id, OrderRequest orderRequest){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order doesn't existed !"));

        orderMapper.update(order, orderRequest);

        return orderMapper.toResponse(orderRepository.save(order));
    }

}
