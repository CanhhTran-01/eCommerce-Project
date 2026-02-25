package com.myproject.ecommerce.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoResponse {
    private String username;
    private String email;
    private LocalDateTime createdAt;
}
