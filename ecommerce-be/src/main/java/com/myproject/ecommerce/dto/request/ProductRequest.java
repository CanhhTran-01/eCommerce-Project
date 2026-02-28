package com.myproject.ecommerce.dto.request;

import java.math.BigDecimal;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String productName;
    private String brand;
    private String shortDescription;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stockQuantity;
}
