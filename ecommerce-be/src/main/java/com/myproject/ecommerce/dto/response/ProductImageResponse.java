package com.myproject.ecommerce.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponse {
    private Long id;
    private String fileName;
    private String imageUrl;
    private Boolean isMain;
    private Integer sortOrder;
    private String altText;
}
