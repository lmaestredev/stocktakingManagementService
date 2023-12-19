package com.stockapp.stocktakingmanagementservice.utils.mappers;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.CustomerDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import com.stockapp.stocktakingmanagementservice.core.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    Customer dtoToEntity(CustomerDtoReq customerDtoReq);

    CustomerDtoReq entityToDtoReq(Customer customer);

    CustomerDtoRes entityToDtoRes(Customer customer);
}
