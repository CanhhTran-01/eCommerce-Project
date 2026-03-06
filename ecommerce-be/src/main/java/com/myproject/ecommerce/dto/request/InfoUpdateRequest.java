package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.Gender;
import com.myproject.ecommerce.validator.DobConstraint;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "PHONE_NUMBER_INVALID")
    private String phoneNumber;

    private Gender gender;

    @DobConstraint(minOld = 10, message = "DATE_OF_BIRTH_INVALID")
    private LocalDate dateOfBirth;

    private String avatarUrl;
    private String address;
}
