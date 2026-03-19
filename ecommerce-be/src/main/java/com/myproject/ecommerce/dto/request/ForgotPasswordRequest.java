package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.OtpType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {

    @NotBlank(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "USERNAME_INVALID")
    @Size(min = 4, max = 20, message = "USERNAME_INVALID")
    private String username;

    @NotBlank
    private OtpType otpType;
}
