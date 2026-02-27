package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.OtpType;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOtpRequest {
    private String email;
    private OtpType otpType;
}
