package com.stockapp.stocktakingmanagementservice.utils.mappers;

import com.stockapp.stocktakingmanagementservice.core.dtos.CustomerDto;
import com.stockapp.stocktakingmanagementservice.core.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    Customer dtoToEntity(CustomerDto customerDto);

    CustomerDto entityToDto(Customer customer);
}
