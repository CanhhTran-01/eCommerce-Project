package com.myproject.ecommerce.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishListRequest {
    private Long productId;
}
