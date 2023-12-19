package com.stockapp.stocktakingmanagementservice.core.dtos.request;

import java.util.List;

public class ProductLotsDtoReq {
    List<ProductDtoReq> productList;

    public ProductLotsDtoReq() {
    }

    public ProductLotsDtoReq(List<ProductDtoReq> productList) {
        this.productList = productList;
    }

    public List<ProductDtoReq> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDtoReq> productList) {
        this.productList = productList;
    }
}
