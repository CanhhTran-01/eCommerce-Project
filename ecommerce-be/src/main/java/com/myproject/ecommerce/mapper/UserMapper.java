package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.UserRequest;
import com.myproject.ecommerce.dto.response.CustomerResponse;
import com.myproject.ecommerce.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserRequest userRequest);
    CustomerResponse toResponse(UserEntity userEntity);
    void updateCustomer(@MappingTarget UserEntity userEntity, UserRequest userRequest);
}
