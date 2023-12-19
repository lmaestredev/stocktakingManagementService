package com.stockapp.stocktakingmanagementservice.core.dtos.response;

import java.math.BigDecimal;

public class ProductDtoRes {

    private String id;
    private String name;
    private Number stockQuantity;
    private BigDecimal cost;
    private Integer code;

    public ProductDtoRes() {
    }

    public ProductDtoRes(String id, String name, Number stockQuantity, BigDecimal cost, Integer code) {
        this.id = id;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.cost = cost;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
