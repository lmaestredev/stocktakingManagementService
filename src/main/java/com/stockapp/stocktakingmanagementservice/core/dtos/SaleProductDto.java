package com.stockapp.stocktakingmanagementservice.core.dtos;

import java.math.BigDecimal;

public class SaleProductDto {

    private String name;
    private Integer code;
    private float quantity;
    private BigDecimal cost;

    public SaleProductDto() {
    }

    public SaleProductDto(String name, Integer code, float quantity, BigDecimal cost) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
