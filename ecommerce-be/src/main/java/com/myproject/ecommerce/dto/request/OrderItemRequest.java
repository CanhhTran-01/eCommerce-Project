package com.myproject.ecommerce.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
