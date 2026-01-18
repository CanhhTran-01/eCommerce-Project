package com.myproject.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Integer rating;
    private String title;
    private String comment;
    private String imageUrl;
    private Long likes;
    private LocalDateTime updatedAt;
}
