package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.CategoryResponse;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.service.CategoryService;
import com.myproject.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category Controller")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @Operation(summary = "get categories (list)")
    @SecurityRequirements
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoryList() {

        var apiResponse = new ApiResponse<>(true, null, categoryService.getCategoryList());
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "get products by category")
    @SecurityRequirements
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getProductsByCategory(
            @PathVariable("categoryId") Long id) {
        var apiResponse = new ApiResponse<>(true, null, productService.getProductsByCategory(id));
        return ResponseEntity.ok(apiResponse);
    }
}
