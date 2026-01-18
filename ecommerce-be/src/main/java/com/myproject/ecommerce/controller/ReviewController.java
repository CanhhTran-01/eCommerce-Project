package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ReviewRequest;
import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/api/reviews")
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.createReview(reviewRequest));
    }

    @GetMapping("/api/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewList(){
        return ResponseEntity.ok(reviewService.getReviewList());
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
