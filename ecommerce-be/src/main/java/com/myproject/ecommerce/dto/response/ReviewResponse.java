package com.myproject.ecommerce.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private String nickName;
    private String title;
    private Integer rating;
    private String comment;
    private LocalDateTime updatedAt;
}
