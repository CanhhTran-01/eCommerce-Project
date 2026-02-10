package com.myproject.ecommerce.dto.response;

import com.myproject.ecommerce.enums.Gender;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
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
