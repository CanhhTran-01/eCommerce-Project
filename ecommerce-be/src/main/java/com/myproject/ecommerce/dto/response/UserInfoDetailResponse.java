package com.myproject.ecommerce.dto.response;

import com.myproject.ecommerce.enums.Gender;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDetailResponse {
    private Long id;
    private String fullName;
    private String nickName;
    private String userCode;
    private String avatarUrl;
    private String phoneNumber;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String address;
    private BigDecimal personalPoints;
}
