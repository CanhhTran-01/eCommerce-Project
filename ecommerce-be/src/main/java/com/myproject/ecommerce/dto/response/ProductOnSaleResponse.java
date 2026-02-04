package com.myproject.ecommerce.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOnSaleResponse {
    private Long id;
    private String productName;
    private BigDecimal price;
    private BigDecimal discountPrice;
}
