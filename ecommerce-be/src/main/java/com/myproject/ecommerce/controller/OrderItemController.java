package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;


    @GetMapping("/me/active")
    public ResponseEntity<ApiResponse<List<OrderItemResponse>>> getActiveOrderItems(@AuthenticationPrincipal Jwt jwt){
        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT

        var apiResponse = new ApiResponse<>(
                true,
                null,
                orderItemService.getActiveOrderItems(accountId)
        );
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/me/history")
    public ResponseEntity<ApiResponse<List<OrderItemResponse>>> getOrderItemsHistory(@AuthenticationPrincipal Jwt jwt){
        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT

        var apiResponse = new ApiResponse<>(
                true,
                null,
                orderItemService.getOrderItemsHistory(accountId)
        );
        return ResponseEntity.ok(apiResponse);
    }

}
