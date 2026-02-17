package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.OrderDetailResponse;
import com.myproject.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


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

}
