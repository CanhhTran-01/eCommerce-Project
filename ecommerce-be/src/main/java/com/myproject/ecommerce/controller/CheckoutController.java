package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.CheckoutRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.CheckoutResponse;
import com.myproject.ecommerce.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CheckoutResponse>> createOrder(
            @RequestBody CheckoutRequest checkoutRequest, @AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT

        var apiResponse = new ApiResponse<>(true, null, checkoutService.handleCODCheckout(accountId, checkoutRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
