package com.myproject.ecommerce.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequest {
    private String fileName;
    private String imageUrl;
    private Integer sortOrder;
    private String altText;
}
