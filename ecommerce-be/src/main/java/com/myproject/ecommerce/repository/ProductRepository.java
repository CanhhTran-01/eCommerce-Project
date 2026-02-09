package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.discountPrice IS NOT NULL AND p.discountPrice < p.price
    """)
    List<Product> getProductOnSaleList();

    @Query(value = """
            SELECT p.*
            FROM product p
            JOIN wish_list w ON p.id = w.product_id
            JOIN account a ON a.user_id = w.user_id
            WHERE a.id = :accountId
    """,
            nativeQuery = true
    )
    List<Product> getWishlistByAccountId(@Param("accountId") Long accountId);

    List<Product> getProductByCategoryId(Long categoryId);

}
