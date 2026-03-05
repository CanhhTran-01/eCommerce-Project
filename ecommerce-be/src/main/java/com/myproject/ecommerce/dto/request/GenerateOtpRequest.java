package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.OtpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOtpRequest {
    private String email;
    private OtpType otpType;
}
