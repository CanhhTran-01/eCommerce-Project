package com.myproject.ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Data
@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @PrePersist
    public void prePersist() {
        this.totalPrice = price.multiply(BigDecimal.valueOf(quantity));
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
