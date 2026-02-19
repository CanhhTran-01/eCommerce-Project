package com.myproject.ecommerce.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterSearchRequest {
    private String searchText;
    private List<Long> categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String sort;
}

