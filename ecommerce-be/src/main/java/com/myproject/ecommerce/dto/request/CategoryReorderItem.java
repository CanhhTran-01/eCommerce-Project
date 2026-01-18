package com.myproject.ecommerce.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReorderItem {
    private Long id;
    private Integer displayOrder;
}
