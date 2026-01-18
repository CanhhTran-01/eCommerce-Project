package com.myproject.ecommerce.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime lastLogin;
}
