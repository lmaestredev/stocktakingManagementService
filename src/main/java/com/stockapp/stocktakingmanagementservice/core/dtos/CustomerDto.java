package com.stockapp.stocktakingmanagementservice.core.dtos;

import lombok.Data;

@Data
public class CustomerDto {

    private String name;

    public CustomerDto() {
    }

    public CustomerDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
