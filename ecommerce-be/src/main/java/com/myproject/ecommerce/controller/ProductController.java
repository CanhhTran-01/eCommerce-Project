package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductSummaryResponse> createProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(productRequest));
    }

    @GetMapping("/sale-list")
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getSaleProductList(){

        var apiResponse = new ApiResponse<>(
                true,
                null,
                productService.getProductOnSaleList()
        );
        return ResponseEntity.ok(apiResponse);
    }

}
