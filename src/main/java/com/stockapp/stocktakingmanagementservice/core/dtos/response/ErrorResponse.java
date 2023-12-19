package com.stockapp.stocktakingmanagementservice.core.dtos.response;

public class ErrorResponse {

    private String error;
    private Object data;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, Object data) {
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
