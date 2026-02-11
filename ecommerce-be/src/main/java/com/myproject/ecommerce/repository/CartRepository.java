package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.dto.response.CartItemResponse;
import com.myproject.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("""
                SELECT new com.myproject.ecommerce.dto.response.CartItemResponse(
                    ci.id,
                    p.id,
                    p.productName,
                    ci.quantity,
                    ci.priceAtPurchase,
                    COALESCE(p.discountPrice, p.price)
                )
                FROM CartItem ci
                JOIN ci.cart c
                JOIN c.user u
                JOIN u.account a
                JOIN Product p ON ci.productId = p.id
                WHERE a.id = :accountId
    """)
    List<CartItemResponse> findCartItemsDtoByAccountId(@Param("accountId") Long accountId);

}
