package com.myproject.ecommerce.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private String imageUrl;
    private String productName;
    private Integer quantity;
    private String status;
    private BigDecimal totalPrice;
}
