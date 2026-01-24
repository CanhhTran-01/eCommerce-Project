package com.myproject.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "invalid_token")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvalidTokenEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "expiry_time")
    private Date expiryTime;

}
