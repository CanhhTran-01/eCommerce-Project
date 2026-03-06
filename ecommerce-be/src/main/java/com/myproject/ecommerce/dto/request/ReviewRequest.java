package com.myproject.ecommerce.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Long productId;

    @NotNull(message = "NO_RATING")
    @Min(value = 1, message = "INVALID_RATING_DATA")
    @Max(value = 5, message = "INVALID_RATING_DATA")
    private Integer rating;

    @Size(max = 100, message = "NO_TITLE_DATA")
    private String title;

    @Size(max = 1000, message = "NO_COMMENT_DATA")
    private String comment;
}
