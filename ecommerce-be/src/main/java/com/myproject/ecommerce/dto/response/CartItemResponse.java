package com.myproject.ecommerce.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long productId;
    private String mainImageUrl;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private boolean checked; // for checkbox in FE
}
