package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.UserRequest;
import com.myproject.ecommerce.dto.response.UserResponse;
import com.myproject.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest userRequest);
    UserResponse toResponse(User user);
    void updateCustomer(@MappingTarget User user, UserRequest userRequest);
}
