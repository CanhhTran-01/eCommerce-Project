package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.Gender;
import com.myproject.ecommerce.validator.DobConstraint;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoUpdateRequest {
    private String nickName;
    private String fullName;
    private String phoneNumber;
    private Gender gender;

    @DobConstraint(min = 10, message = "Date of birth must be at least 10 years old")
    private LocalDate dateOfBirth;

    private String avatarUrl;
    private String address;
}
