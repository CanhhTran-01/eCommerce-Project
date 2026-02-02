package com.myproject.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_item")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_at_purchase")
    private BigDecimal priceAtPurchase;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() {
        this.addedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
