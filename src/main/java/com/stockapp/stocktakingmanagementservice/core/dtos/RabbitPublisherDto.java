package com.stockapp.stocktakingmanagementservice.core.dtos;

public class RabbitPublisherDto {
    private String casoDeUso;
    private Object data;

    private String error;

    public RabbitPublisherDto() {
    }

    public RabbitPublisherDto(String casoDeUso, Object data) {
        this.casoDeUso = casoDeUso;
        this.data = data;
    }

    public RabbitPublisherDto(String casoDeUso, Object data, String error) {
        this.casoDeUso = casoDeUso;
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCasoDeUso() {
        return casoDeUso;
    }

    public void setCasoDeUso(String casoDeUso) {
        this.casoDeUso = casoDeUso;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
