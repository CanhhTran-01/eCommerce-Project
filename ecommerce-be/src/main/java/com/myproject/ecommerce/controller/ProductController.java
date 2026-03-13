package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ProductFilterSearchRequest;
import com.myproject.ecommerce.dto.response.*;
import com.myproject.ecommerce.service.ProductService;
import com.myproject.ecommerce.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    @Operation(summary = "get sale products (list)")
    @SecurityRequirements
    @GetMapping("/sale-list")
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getSaleProductList() {

        var apiResponse = new ApiResponse<>(true, null, productService.getProductOnSaleList());
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "get product detail")
    @SecurityRequirements
    @GetMapping("/{id}/detail")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductDetail(@PathVariable Long id) {

        var apiResponse = new ApiResponse<>(true, null, productService.getDetail(id));
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "get product reviews")
    @SecurityRequirements
    @GetMapping("/{id}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getProductReviews(@PathVariable Long id) {

        var apiResponse = new ApiResponse<>(true, null, reviewService.getProductReviews(id));
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "get related products")
    @SecurityRequirements
    @GetMapping("/{productId}/related")
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getRelatedProducts(
            @PathVariable("productId") Long productId) {

        var apiResponse = new ApiResponse<>(true, null, productService.getRelatedProducts(productId));
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "get product by filter")
    @SecurityRequirements
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getProductsByFilterSearch(
            ProductFilterSearchRequest request) {

        var apiResponse = new ApiResponse<>(true, null, productService.getFilterSearchProduct(request));
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "keywords and products suggestion")
    @SecurityRequirements
    @GetMapping("/suggestion")
    public ResponseEntity<ApiResponse<SuggestionResponse>> suggestProductAndText(
            @RequestParam("keyword") String keyword) {

        var apiResponse = new ApiResponse<>(true, null, productService.getTextsAndProductsSuggestion(keyword));
        return ResponseEntity.ok(apiResponse);
    }
}
