package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.CartItemRequest;
import com.myproject.ecommerce.dto.response.CartItemResponse;
import com.myproject.ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/api/cart_items")
    public ResponseEntity<CartItemResponse> createCartItem(@RequestBody CartItemRequest cartItemRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @DeleteMapping("/api/cart_items/{id}")
    public String deleteCart(@PathVariable Long id){
        return ("Deleted cart item with id + " + id + " ! ");
    }
}
