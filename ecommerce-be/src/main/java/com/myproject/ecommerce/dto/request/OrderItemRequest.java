package com.myproject.ecommerce.dto.request;

import java.math.BigDecimal;
import lombok.*;

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
