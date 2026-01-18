package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.OrderRequest;
import com.myproject.ecommerce.dto.response.OrderResponse;
import com.myproject.ecommerce.entity.OrderEntity;
import com.myproject.ecommerce.mapper.OrderEntityMapper;
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
    private final OrderEntityMapper orderEntityMapper;

    public OrderResponse createOrder(OrderRequest orderRequest){
        OrderEntity orderEntity = orderEntityMapper.toEntity(orderRequest);

        return orderEntityMapper.toResponse(orderRepository.save(orderEntity));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrderList(){
        return orderRepository.findAll()
                .stream()
                .map(orderEntityMapper::toResponse)
                .toList();
    }

    public OrderResponse updateOrder(Long id, OrderRequest orderRequest){
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order doesn't existed !"));

        orderEntityMapper.update(orderEntity, orderRequest);

        return orderEntityMapper.toResponse(orderRepository.save(orderEntity));
    }

}
