package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.OtpType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "PASSWORD_INVALID")
    private String oldPassword;

    @NotBlank(message = "PASSWORD_INVALID")
    @Size(min = 8, max = 50, message = "PASSWORD_INVALID")
    private String newPassword;

    @NotNull(message = "OTP_INVALID")
    private OtpType otpType;
}
