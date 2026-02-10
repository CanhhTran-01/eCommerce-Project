package com.myproject.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private String nickName;
    private String title;
    private Integer rating;
    private String comment;
    private LocalDateTime updatedAt;
}
