package com.myproject.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Email(message = "EMAIL_INVALID")
    private String email;

    @Size(min = 5, message = "USERNAME_INVALID")
    private String username;

    @Size(min = 5, message = "PASSWORD_INVALID")
    private String password;
}
