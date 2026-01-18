package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.PaymentRequest;
import com.myproject.ecommerce.dto.response.PaymentResponse;
import com.myproject.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/api/payments")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

    @GetMapping("/api/payments")
    public ResponseEntity<List<PaymentResponse>> getPaymentList(){
        return ResponseEntity.ok(null);
    }

}
