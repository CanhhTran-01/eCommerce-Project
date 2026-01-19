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
    @Size(min = 5, message = "USERNAME_INVALID")
    private String username;

    @Email(message = "EMAIL_INVALID")
    private String email;

    @Size(min = 5, message = "PASSWORD_INVALID")
    private String password;
}
