package com.myproject.ecommerce.dto.response;

import java.math.BigDecimal;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryResponse {
    private Long id;
    private String productName;
    private String mainImageUrl;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Long ratingCount;
    private Double ratingAvg;
}
