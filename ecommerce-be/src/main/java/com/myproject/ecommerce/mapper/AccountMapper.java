package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountEntity toEntity(AccountRequest accountRequest);
    AccountResponse toResponse(AccountEntity accountEntity);
    void updateAccount(@MappingTarget AccountEntity accountEntity, AccountRequest accountRequest);
}
