package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.ThumbnailImageResponse;
import com.myproject.ecommerce.service.ProductGalleryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-gallery")
@RequiredArgsConstructor
public class ProductGalleryController {
    private final ProductGalleryService productGalleryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ThumbnailImageResponse>>> getProductGallery(
            @RequestParam("productId") Long productId) {

        var apiResponse = new ApiResponse<>(true, null, productGalleryService.getProductGallery(productId));
        return ResponseEntity.ok(apiResponse);
    }
}
