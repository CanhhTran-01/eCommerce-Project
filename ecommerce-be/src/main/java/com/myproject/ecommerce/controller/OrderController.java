package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.OrderRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.OrderDetailResponse;
import com.myproject.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<OrderDetailResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(orderRequest));
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrderDetail(
            @RequestParam Long orderId,
            @AuthenticationPrincipal Jwt jwt
    ){

        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT
        var apiResponse = new ApiResponse<>(
                true,
                null,
                orderService.getOrderDetail(accountId, orderId)
        );
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("")
    public ResponseEntity<List<OrderDetailResponse>> getOrderList(){
        return ResponseEntity.ok(orderService.getOrderList());
    }


    @PutMapping("")
    public ResponseEntity<OrderDetailResponse> updateOrder(@PathVariable Long id,
                                                           @RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.updateOrder(id, orderRequest));
    }

}
