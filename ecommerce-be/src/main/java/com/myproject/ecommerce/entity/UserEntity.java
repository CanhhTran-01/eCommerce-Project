package com.myproject.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user_customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "user_code", unique = true)
    private String customerCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Lob
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "address")
    private String address;

    @Column(name = "personal_points")
    private BigDecimal personalPoints;

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

    @OneToOne(mappedBy = "userEntity")
    private AccountEntity accountEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<CartEntity> cartEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<OrderEntity> orderEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "wish_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> wishList = new HashSet<>();

}
