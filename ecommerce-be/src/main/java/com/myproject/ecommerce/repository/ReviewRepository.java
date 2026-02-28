package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.dto.response.ReviewResponse;
import com.myproject.ecommerce.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(
            """
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

    boolean existsByProductIdAndUserId(Long productId, Long userId);
}
