package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(AccountRequest accountRequest);
    AccountResponse toResponse(Account account);
    void updateAccount(@MappingTarget Account account, AccountRequest accountRequest);
}
