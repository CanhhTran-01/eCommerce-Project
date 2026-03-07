package com.myproject.ecommerce.dto.response;

import com.myproject.ecommerce.enums.AccountStatus;
import com.myproject.ecommerce.enums.Role;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoSummaryResponse {
    private Long id;
    private String userCode;
    private String avatarUrl;
    private String nickName;
    private Set<Role> accountRoles;
    private AccountStatus accountStatus;
}
