package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.OrderDetailResponse;
import com.myproject.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "get order detail")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrderDetail(
            @RequestParam Long orderId, @AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        var apiResponse = new ApiResponse<>(true, null, orderService.getOrderDetail(accountId, orderId));
        return ResponseEntity.ok(apiResponse);
    }
}
