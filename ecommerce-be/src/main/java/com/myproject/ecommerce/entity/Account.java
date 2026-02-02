package com.myproject.ecommerce.entity;

import com.myproject.ecommerce.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="account")
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id")
    )
    @Column(name = "role")
    private Set<Role> accountRoles;

    @Column(name="last_login")
    private LocalDateTime lastLogin;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

}
