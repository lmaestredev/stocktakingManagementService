package com.stockapp.stocktakingmanagementservice.core.dtos.response;

import com.stockapp.stocktakingmanagementservice.utils.enums.SaleType;

import java.math.BigDecimal;
import java.util.List;

public class SaleDtoRes {

    private String id;
    private Object customer;
    private List<Object> products;
    private BigDecimal amount;
    private SaleType saleType;

    public SaleDtoRes() {
    }

    public SaleDtoRes(String id, Object customer, List<Object> products, BigDecimal amount, SaleType saleType) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.amount = amount;
        this.saleType = saleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getCustomer() {
        return customer;
    }

    public void setCustomer(Object customer) {
        this.customer = customer;
    }

    public List<Object> getProducts() {
        return products;
    }

    public void setProducts(List<Object> products) {
        this.products = products;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }
}
