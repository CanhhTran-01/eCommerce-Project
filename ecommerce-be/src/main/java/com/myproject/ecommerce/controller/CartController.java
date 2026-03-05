package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.AddToCartRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.CartItemResponse;
import com.myproject.ecommerce.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/me/item")
    public ResponseEntity<ApiResponse<?>> addItemToCart(
            @RequestBody AddToCartRequest addToCartRequest, @AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        cartService.addItemToCart(accountId, addToCartRequest);

        var apiResponse = new ApiResponse<>(true, "Đã thêm sản phẩm vào giỏ hàng !", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/me/items")
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getCartItems(@AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT

        var apiResponse = new ApiResponse<>(true, null, cartService.getCartItems(accountId));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/me/items/{productId}")
    public ResponseEntity<ApiResponse<?>> deleteItemFromCart(
            @AuthenticationPrincipal Jwt jwt, @PathVariable Long productId) {

        Long accountId = jwt.getClaim("accountId");
        cartService.deleteItemFromCart(accountId, productId);

        var apiResponse = new ApiResponse<>(true, null, null);
        return ResponseEntity.noContent().build();
    }
}
