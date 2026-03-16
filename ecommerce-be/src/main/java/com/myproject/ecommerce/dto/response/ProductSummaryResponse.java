package com.myproject.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSummaryResponse {
    private Long id;
    private String productName;
    private String mainImageUrl;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Long ratingCount;
    private Double ratingAvg;
}
