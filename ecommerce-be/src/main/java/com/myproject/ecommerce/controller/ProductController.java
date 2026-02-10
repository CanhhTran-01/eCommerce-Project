package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.ProductDetailResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.service.ProductService;
import com.myproject.ecommerce.service.ReviewService;
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
    private final ReviewService reviewService;


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


    @GetMapping("/{id}/detail")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductDetail(@PathVariable Long id){

        var apiResponse = new ApiResponse<>(
                true,
                null,
                productService.getDetail(id)
        );
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getProductReviews(@PathVariable Long id){

        var apiResponse = new ApiResponse<>(
                true,
                null,
                reviewService.getProductReviews(id)
        );
        return ResponseEntity.ok(apiResponse);
    }
}
