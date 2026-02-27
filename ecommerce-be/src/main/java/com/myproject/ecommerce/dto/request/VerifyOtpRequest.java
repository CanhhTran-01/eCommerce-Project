package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.OtpType;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpRequest {
    private String email;
    private String otp;
    private OtpType otpType;
}
