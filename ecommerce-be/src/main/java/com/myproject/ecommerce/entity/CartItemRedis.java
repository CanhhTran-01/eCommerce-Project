package com.myproject.ecommerce.entity;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRedis {
    private Long productId;
    private BigDecimal priceAtAdd;
}
