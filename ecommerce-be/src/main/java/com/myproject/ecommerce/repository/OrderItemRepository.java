package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // take order-item by account(user) with status field from order
    @Query("""
       SELECT new com.myproject.ecommerce.dto.response.OrderItemResponse(
            oi.id,
            oi.order.id,
            oi.imageUrl,
            oi.productName,
            oi.quantity,
            oi.order.status,
            oi.totalPrice
       )
       FROM OrderItem oi
       WHERE oi.order.user.account.id = :accountId AND oi.order.status IN ('PENDING', 'CONFIRMED', 'SHIPPING')
       """)
    List<OrderItemResponse> findActiveOrderItemsByAccountId(@Param("accountId") Long accountId);


    // get purchase history
    @Query("""
       SELECT new com.myproject.ecommerce.dto.response.OrderItemResponse(
            oi.id,
            oi.order.id,
            oi.imageUrl,
            oi.productName,
            oi.quantity,
            oi.order.status,
            oi.totalPrice
       )
       FROM OrderItem oi
       WHERE oi.order.user.account.id = :accountId AND oi.order.status IN ('COMPLETED', 'CANCELED')
       """)
    List<OrderItemResponse> getOrderItemsHistory(@Param("accountId") Long accountId);
}
