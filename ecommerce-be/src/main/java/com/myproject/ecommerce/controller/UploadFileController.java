package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.service.UploadFileService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadFileController {
    private final UploadFileService uploadFileService;

    @PostMapping("/avatar-image")
    public ResponseEntity<ApiResponse<String>> uploadAvatarForProfile(
            @RequestParam("image") MultipartFile file, @AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        var apiResponse =
                new ApiResponse<>(true, "Upload thành công !", uploadFileService.uploadAvatarImage(accountId, file));
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/categories/{categoryId}/image")
    public ResponseEntity<ApiResponse<String>> uploadImageForCategory(
            @RequestParam("image") MultipartFile file, @PathVariable("categoryId") Long categoryId) {

        var apiResponse =
                new ApiResponse<>(true, "Upload thành công !", uploadFileService.uploadCategoryImage(categoryId, file));
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/products/{productId}/images")
    public ResponseEntity<ApiResponse<?>> uploadImagesForProduct(
            @RequestParam("images") List<MultipartFile> files, @PathVariable("productId") Long productId) {

        uploadFileService.uploadProductImages(productId, files);
        var apiResponse = new ApiResponse<>(true, "Upload thành công !", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
