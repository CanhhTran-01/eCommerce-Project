package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ReviewRequest;
import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.entity.ReviewEntity;
import com.myproject.ecommerce.mapper.ReviewEntityMapper;
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
    private final ReviewEntityMapper reviewEntityMapper;

    public ReviewResponse createReview(ReviewRequest reviewRequest){
        ReviewEntity reviewEntity = reviewEntityMapper.toEntity(reviewRequest);
        return reviewEntityMapper.toResponse(reviewRepository.save(reviewEntity));
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewList(){
        return reviewRepository.findAll()
                .stream()
                .map(reviewEntityMapper::toResponse)
                .toList();
    }

    public ReviewResponse updateReview(Long id, ReviewRequest reviewRequest){
        ReviewEntity reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found !"));

        reviewEntityMapper.update(reviewEntity, reviewRequest);
        return reviewEntityMapper.toResponse(reviewEntity);
    }

    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
    }
}
