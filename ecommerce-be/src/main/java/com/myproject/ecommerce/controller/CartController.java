package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.CartRequest;
import com.myproject.ecommerce.dto.response.CartResponse;
import com.myproject.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/api/carts")
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest cartRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @DeleteMapping("/api/carts/{id}")
    public String deleteCart(@PathVariable Long id){
        return ("Deleted cart with id + " + id + " ! ");
    }

}
