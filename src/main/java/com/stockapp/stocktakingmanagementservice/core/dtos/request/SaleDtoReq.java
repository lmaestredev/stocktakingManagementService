package com.stockapp.stocktakingmanagementservice.core.dtos.request;

import com.stockapp.stocktakingmanagementservice.core.dtos.SaleProductDto;
import com.stockapp.stocktakingmanagementservice.utils.enums.SaleType;

import java.util.List;

public class SaleDtoReq {

    private String customerName;
    private List<SaleProductDto> products;
    private String saleType;

    public SaleDtoReq() {
    }

    public SaleDtoReq(String customerName, List<SaleProductDto> products, String saleType) {
        this.customerName = customerName;
        this.products = products;
        this.saleType = saleType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<SaleProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<SaleProductDto> products) {
        this.products = products;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }
}
