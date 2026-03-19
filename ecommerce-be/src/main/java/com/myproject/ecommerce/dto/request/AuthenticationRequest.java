package com.myproject.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "USERNAME_INVALID")
    @Size(min = 4, max = 20, message = "USERNAME_INVALID")
    private String username;

    @NotBlank(message = "PASSWORD_INVALID")
    @Size(min = 8, max = 50, message = "PASSWORD_INVALID")
    private String password;
}
