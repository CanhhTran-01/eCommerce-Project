package com.myproject.ecommerce.dto.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private String categoryImage;
}
