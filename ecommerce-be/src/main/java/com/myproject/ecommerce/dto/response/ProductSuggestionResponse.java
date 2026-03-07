package com.myproject.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSuggestionResponse {
    private Long id;
    private String productName;
    private String mainImageUrl;
}
