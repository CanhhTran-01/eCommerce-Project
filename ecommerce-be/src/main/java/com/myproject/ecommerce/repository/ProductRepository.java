package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.repository.custom.ProductRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    @Query(
            """
                    		SELECT new com.myproject.ecommerce.dto.response.ProductSummaryResponse(
                    			p.id,
                    			p.productName,
                    			p.mainImageUrl,
                    			p.price,
                    			p.discountPrice,
                    			COUNT(r),
                    			COALESCE(AVG(r.rating), 0.0)
                    		)
                    		FROM Product p
                    		LEFT JOIN p.reviewList r
                    		WHERE p.discountPrice IS NOT NULL AND p.discountPrice < p.price
                    		GROUP BY p.id, p.productName, p.mainImageUrl, p.price, p.discountPrice
                    """)
    List<ProductSummaryResponse> getProductOnSaleList();

    @Query(
            """
                    		SELECT new com.myproject.ecommerce.dto.response.ProductSummaryResponse(
                    			p.id,
                    			p.productName,
                    			p.mainImageUrl,
                    			p.price,
                    			p.discountPrice,
                    			COUNT(r.id),
                    			COALESCE(AVG(r.rating), 0.0)
                    		)
                    		FROM Account a
                    		JOIN a.user u
                    		JOIN u.wishList p
                    		LEFT JOIN Review r ON r.product.id = p.id
                    		WHERE a.id = :accountId
                    		GROUP BY p.id, p.productName, p.mainImageUrl, p.price, p.discountPrice
                    """)
    List<ProductSummaryResponse> getWishlistByAccountId(@Param("accountId") Long accountId);

    @Query(
            """
                    		SELECT new com.myproject.ecommerce.dto.response.ProductSummaryResponse(
                    			p.id,
                    			p.productName,
                    			p.mainImageUrl,
                    			p.price,
                    			p.discountPrice,
                    			COUNT(r),
                    			COALESCE(AVG(r.rating), 0.0)
                    		)
                    		FROM Product p
                    		LEFT JOIN p.reviewList r
                    		WHERE p.category.id = :categoryId
                    		GROUP BY (p.id, p.productName, p.mainImageUrl, p.price, p.discountPrice)
                    """)
    List<ProductSummaryResponse> getProductsByCategoryId(Long categoryId);

    boolean existsByIdAndWishedByAccountId(Long productId, Long accountId);
}
