package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ProductImageRequest;
import com.myproject.ecommerce.dto.response.ProductImageResponse;
import com.myproject.ecommerce.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping("/api/product_images")
    public ResponseEntity<ProductImageResponse> createProductImage(@RequestBody ProductImageRequest productImageRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productImageService.createProductImage(productImageRequest));
    }

    @GetMapping("/api/product_images")
    public ResponseEntity<List<ProductImageResponse>> getProductImagesList(){
        return ResponseEntity.ok(productImageService.getProductImageList());
    }

    @PutMapping("/api/product_images/{id}")
    public ResponseEntity<ProductImageResponse> updateProductImage(@PathVariable Long id,
                                                                   @RequestBody ProductImageRequest productImageRequest){
        return ResponseEntity.ok(productImageService.updateProductImage(id, productImageRequest));
    }

    @DeleteMapping("/api/product_images/{id}")
    public String deleteProductImage(@PathVariable Long id){
        productImageService.deleteProductImage(id);
        return ("Deleted image with id " + id + " ! ");
    }

}
