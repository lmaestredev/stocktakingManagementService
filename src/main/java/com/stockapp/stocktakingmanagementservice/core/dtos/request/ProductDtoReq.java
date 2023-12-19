package com.stockapp.stocktakingmanagementservice.core.dtos.request;

import java.math.BigDecimal;

public class ProductDtoReq {

    //    @NotNull(message = "El campo 'precio' no puede ser nulo")
    private String name;
    private Number stockQuantity;
    private BigDecimal cost;
    private Integer code;

    public ProductDtoReq() {
    }

    public ProductDtoReq(String name, Number stockQuantity, BigDecimal cost, Integer code) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.cost = cost;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Number stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
