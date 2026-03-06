package com.myproject.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "USERNAME_INVALID")
    @Size(min = 5, max = 30, message = "USERNAME_INVALID")
    private String username;

    @NotBlank(message = "PASSWORD_INVALID")
    @Size(min = 8, max = 50, message = "PASSWORD_INVALID")
    private String password;
}
