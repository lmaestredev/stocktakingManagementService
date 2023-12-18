package com.stockapp.stocktakingmanagementservice.core.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("Products")
public class Product {

    @Id
    private String id;

    @Indexed(unique=true)
    private String name;
    private Number stockQuantity;
    private BigDecimal cost;
    private Integer code;

    public Product() {
    }

    public Product(String name, Number stockQuantity, BigDecimal cost, Integer code) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.cost = cost;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Number getStockQuantity() {
        return stockQuantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Integer getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStockQuantity(Number stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
