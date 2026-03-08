package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.InfoUpdateRequest;
import com.myproject.ecommerce.dto.response.UserInfoDetailResponse;
import com.myproject.ecommerce.dto.response.UserInfoSummaryResponse;
import com.myproject.ecommerce.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(InfoUpdateRequest infoUpdateRequest);

    UserInfoDetailResponse toInfoResponse(User user);

    UserInfoSummaryResponse toSummaryResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, InfoUpdateRequest infoUpdateRequest);
}
