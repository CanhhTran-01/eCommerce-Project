package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
            SELECT new com.myproject.ecommerce.dto.response.ReviewResponse(
                r.user.nickName,
                r.title,
                r.rating,
                r.comment,
                r.updatedAt
            )
            FROM Review r
            WHERE r.product.id = :productId
            ORDER BY r.updatedAt DESC
        """)
    List<ReviewResponse> findReviewsByProductId(@Param("productId") Long productId);
}
