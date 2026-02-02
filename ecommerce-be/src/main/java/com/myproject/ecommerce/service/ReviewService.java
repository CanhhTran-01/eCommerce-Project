package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ReviewRequest;
import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.entity.Review;
import com.myproject.ecommerce.mapper.ReviewMapper;
import com.myproject.ecommerce.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewResponse createReview(ReviewRequest reviewRequest){
        Review review = reviewMapper.toEntity(reviewRequest);
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewList(){
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    public ReviewResponse updateReview(Long id, ReviewRequest reviewRequest){
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found !"));

        reviewMapper.update(review, reviewRequest);
        return reviewMapper.toResponse(review);
    }

    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
    }
}
