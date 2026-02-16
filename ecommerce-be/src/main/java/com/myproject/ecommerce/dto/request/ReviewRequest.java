package com.myproject.ecommerce.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Long productId;
    private Integer rating;
    private String title;
    private String comment;
}
