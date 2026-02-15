package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.id = :orderId
            AND o.user.account.id = :accountId
       """)
    Optional<Order> findOrderByIdAndAccountId(
            @Param("accountId") Long accountId,
            @Param("orderId") Long orderId
    );

}
