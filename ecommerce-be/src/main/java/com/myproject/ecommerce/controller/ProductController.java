package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ProductRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.ProductOnSaleResponse;
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
    public ResponseEntity<ProductOnSaleResponse> createProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(productRequest));
    }

    @GetMapping("/sale-list")
    public ResponseEntity<ApiResponse<List<ProductOnSaleResponse>>> getSaleProductList(){

        var apiResponse = new ApiResponse<>(
                true,
                null,
                productService.getProductOnSaleList()
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOnSaleResponse> updateProduct(@PathVariable Long id,
                                                               @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.updateProject(id, productRequest));
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ("Deleted Product with id " + id + " ! ");
    }
}
