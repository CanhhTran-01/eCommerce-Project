package com.myproject.ecommerce.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private Long id;
    private String categoryName;
    private String categoryDescription;
    private Integer displayOrder;
}
