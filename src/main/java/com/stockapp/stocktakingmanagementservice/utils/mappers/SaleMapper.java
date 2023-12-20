package com.stockapp.stocktakingmanagementservice.utils.mappers;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.SaleDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.SaleDtoRes;
import com.stockapp.stocktakingmanagementservice.core.models.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SaleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "saleType", ignore = true)
    Sale dtoToEntity(SaleDtoReq saleDtoReq);

    @Mapping(target = "customerName", ignore = true)
    @Mapping(target = "products", ignore = true)
    SaleDtoReq entityToDtoReq(Sale sale);

    SaleDtoRes entityToDtoRes(Sale sale);
}
