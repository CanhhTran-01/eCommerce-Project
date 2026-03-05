package com.myproject.ecommerce.dto.response;

import com.myproject.ecommerce.enums.OrderStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long orderId;
    private Long productId;
    private String imageUrl;
    private String productName;
    private Integer quantity;
    private OrderStatus status;
    private BigDecimal totalPrice;
}
