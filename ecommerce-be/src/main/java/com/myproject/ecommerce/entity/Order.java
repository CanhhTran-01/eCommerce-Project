package com.myproject.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "final_amount")
    private BigDecimal finalAmount;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
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
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Payment payment;

}
