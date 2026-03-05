package com.myproject.ecommerce.dto.request;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterSearchRequest {
    private String searchText;
    private List<Long> categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String sort;
}
