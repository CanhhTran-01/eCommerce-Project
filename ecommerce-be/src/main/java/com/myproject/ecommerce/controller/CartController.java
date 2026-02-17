package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.CartRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.CartItemResponse;
import com.myproject.ecommerce.dto.response.CartResponse;
import com.myproject.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    @PostMapping("")
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest cartRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }


    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getCartItems(@AuthenticationPrincipal Jwt jwt){

        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT
        var apiResponse = new ApiResponse<>(
                true,
                null,
                cartService.getCartItems(accountId)
        );
        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/api/carts/{id}")
    public String deleteCart(@PathVariable Long id){
        return ("Deleted cart with id + " + id + " ! ");
    }

}
