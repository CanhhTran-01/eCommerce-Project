package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.ThumbnailImageResponse;
import com.myproject.ecommerce.service.ProductGalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product Gallery Controller")
public class ProductGalleryController {
    private final ProductGalleryService productGalleryService;

    @Operation(summary = "get product Gallery")
    @SecurityRequirements
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ThumbnailImageResponse>>> getProductGallery(
            @RequestParam("productId") Long productId) {

        var apiResponse = new ApiResponse<>(true, null, productGalleryService.getProductGallery(productId));
        return ResponseEntity.ok(apiResponse);
    }
}
