package com.myproject.ecommerce.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "invalid_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvalidToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "expiry_time")
    private Date expiryTime;
}
