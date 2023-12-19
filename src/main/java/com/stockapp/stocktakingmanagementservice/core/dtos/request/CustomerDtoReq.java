package com.stockapp.stocktakingmanagementservice.core.dtos.request;

public class CustomerDtoReq {

    private String name;

    public CustomerDtoReq() {
    }

    public CustomerDtoReq(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
