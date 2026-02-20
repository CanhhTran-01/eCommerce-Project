package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.service.UploadFileService;
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
            @RequestParam("image") MultipartFile file,
            @AuthenticationPrincipal Jwt jwt
    ){

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        var apiResponse = new ApiResponse<>(
                true,
                null,
                uploadFileService.uploadAvatarImage(accountId, file)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
