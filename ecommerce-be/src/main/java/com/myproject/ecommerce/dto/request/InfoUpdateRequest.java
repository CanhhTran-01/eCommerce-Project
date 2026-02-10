package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoUpdateRequest {
    private String nickName;
    private String fullName;
    private String phoneNumber;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String address;
}
