package com.stockapp.stocktakingmanagementservice.utils.mappers;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.ProductDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.ProductDtoRes;
import com.stockapp.stocktakingmanagementservice.core.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product dtoToEntity(ProductDtoReq productDtoReq);

    ProductDtoReq entityToDtoReq(Product product);

    ProductDtoRes entityToDtoRes(Product product);
}
