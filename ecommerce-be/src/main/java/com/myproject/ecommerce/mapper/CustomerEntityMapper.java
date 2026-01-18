package com.myproject.ecommerce.mapper;

import com.myproject.ecommerce.dto.request.CustomerRequest;
import com.myproject.ecommerce.dto.response.CustomerResponse;
import com.myproject.ecommerce.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {
    CustomerEntity toEntity(CustomerRequest customerRequest);
    CustomerResponse toResponse(CustomerEntity customerEntity);
    void updateCustomer(@MappingTarget CustomerEntity customerEntity, CustomerRequest customerRequest);
}
