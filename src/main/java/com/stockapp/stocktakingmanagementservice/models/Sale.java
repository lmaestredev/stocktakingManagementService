package com.stockapp.stocktakingmanagementservice.models;

import com.stockapp.stocktakingmanagementservice.utils.enums.SaleType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;


@Document("Sales")
public class Sale {

    @Id
    private String id;
    private Object customer;
    private List<Object> products;
    private BigDecimal amount;
    private SaleType saleType;

    private Sale(Builder builder) {
       this.customer = builder.customer;
       this.products = builder.products;
       this.amount = builder.amount;
       this.saleType = builder.saleType;
    }

    public Sale() {}

    //getters
    public String getId() {
        return id;
    }

    public Object getCustomer() {
        return customer;
    }

    public List<Object> getProducts() {
        return products;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    //setters
    public void setId(String id) {
        this.id = id;
    }

    public void setCustomer(Object customer) {
        this.customer = customer;
    }

    public void setProducts(List<Object> products) {
        this.products = products;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }


    public static class Builder{
        private Object customer;
        private List<Object> products;
        private BigDecimal amount;
        private SaleType saleType;

        public Builder(){
        }

        public Builder setCustomer(Object customer){
            this.customer = customer;
            return this;
        }


        public Builder setProducts(List<Object> products){
            this.products = products;
            return this;
        }

        public Builder setAmount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public Builder setSaleType(SaleType saleType){
            this.saleType = saleType;
            return this;
        }

        public Sale build() {
            return new Sale(this);
        }
    }
}
