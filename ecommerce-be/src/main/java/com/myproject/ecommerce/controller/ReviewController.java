package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ReviewRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> createReview(
            @Valid @RequestBody ReviewRequest reviewRequest, @AuthenticationPrincipal Jwt jwt) {
        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        reviewService.createReview(accountId, reviewRequest);
        var apiResponse = new ApiResponse<Void>(true, null, null);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
