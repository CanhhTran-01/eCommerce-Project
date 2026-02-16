package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ReviewRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> createReview(
            @RequestBody ReviewRequest reviewRequest,
            @AuthenticationPrincipal Jwt jwt
    ){
        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT
        reviewService.createReview(accountId, reviewRequest);
        var apiResponse = new ApiResponse<Void>(
                true,
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @PutMapping("/api/reviews/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id,
                                                       @RequestBody ReviewRequest reviewRequest){
        return ResponseEntity.ok(reviewService.updateReview(id, reviewRequest));
    }


    @DeleteMapping("/api/reviews/{id}")
    public String deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return ("Deleted review with id " + id + " ! ");
    }
}
