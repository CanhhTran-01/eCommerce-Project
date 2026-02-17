package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ReviewRequest;
import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.entity.Review;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.mapper.ReviewMapper;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.ProductRepository;
import com.myproject.ecommerce.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;


    // create review
    public void createReview(Long accountId, ReviewRequest reviewRequest){

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

        Product product = productRepository.findById(reviewRequest.getProductId())
                .orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        User user = account.getUser();

        boolean exist = reviewRepository.existsByProductIdAndUserId(reviewRequest.getProductId(), user.getId());
        if (exist){
            throw  new BaseException(ErrorCode.USER_REVIEWED);
        }

        Review review = reviewMapper.toEntity(reviewRequest);
        review.setUser(user);
        review.setProduct(product);

        reviewRepository.save(review);
    }


    // get reviews by product
    @Transactional(readOnly = true)
    public List<ReviewResponse> getProductReviews(Long productId){
        return reviewRepository.findReviewsByProductId(productId);
    }

}
