package com.myproject.ecommerce.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String productName;
    private String mainImageUrl;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stockQuantity;
    private String description;
    private String brand;
    private String color;
    private String madeIn;
    private String shortDescription;
}
