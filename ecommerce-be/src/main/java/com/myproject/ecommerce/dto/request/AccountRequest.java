package com.myproject.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @Size(min = 5, message = "Username must be at least 5 characters")
    private String username;

    @Email(message = "Incorrect email format")
    private String email;

    @Size(min = 5, message = "Password must be at least 5 characters")
    private String password;
}
