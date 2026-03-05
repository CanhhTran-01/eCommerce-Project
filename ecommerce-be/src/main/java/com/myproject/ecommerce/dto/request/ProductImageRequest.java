package com.myproject.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequest {
    private String fileName;
    private String imageUrl;
    private Integer sortOrder;
    private String altText;
}
