package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.SignUpRequest;
import com.myproject.ecommerce.dto.response.AccountInfoResponse;
import com.myproject.ecommerce.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(SignUpRequest signUpRequest);
    AccountInfoResponse toInfoResponse(Account account);
    void updateAccount(@MappingTarget Account account, SignUpRequest signUpRequest);
}
