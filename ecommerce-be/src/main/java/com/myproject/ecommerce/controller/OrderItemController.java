package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.OrderItemRequest;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/api/order_items")
    public ResponseEntity<OrderItemResponse> createOrder(@RequestBody OrderItemRequest orderItemRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderItemService.createOrderItem(orderItemRequest));
    }

    @GetMapping("/api/order_items/{id}")
    public ResponseEntity<OrderItemResponse> getOrderItem(@PathVariable Long id){
        return ResponseEntity.ok(orderItemService.getOrderItem(id));
    }

    @PutMapping("/api/order_items/{id}")
    public ResponseEntity<OrderItemResponse> updateOrder(@PathVariable Long id,
                                                         @RequestBody OrderItemRequest orderItemRequest){
        return ResponseEntity.ok(orderItemService.updateOrderItem(id, orderItemRequest));
    }
}
