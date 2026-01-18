package com.myproject.ecommerce.dto.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReorderRequest {
    private List<CategoryReorderItem> categoryReorderRequestList;
}
