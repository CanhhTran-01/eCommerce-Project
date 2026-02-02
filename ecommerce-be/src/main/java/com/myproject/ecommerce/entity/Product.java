package com.myproject.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code", unique = true)
    private String productCode;

    @Column(name = "brand")
    private String brand;

    @Lob
    @Column(name = "short_description")
    private String shortDescription;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImageList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Review> reviewList = new ArrayList<>();

    @ManyToMany(mappedBy = "wishList")
    private Set<User> wishedBy = new HashSet<>();
}
