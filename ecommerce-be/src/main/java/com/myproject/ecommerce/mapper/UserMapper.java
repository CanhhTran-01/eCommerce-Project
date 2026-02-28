package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.InfoUpdateRequest;
import com.myproject.ecommerce.dto.response.UserInfoResponse;
import com.myproject.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(InfoUpdateRequest infoUpdateRequest);

    UserInfoResponse toInfoResponse(User user);

    void updateUser(@MappingTarget User user, InfoUpdateRequest infoUpdateRequest);
}
