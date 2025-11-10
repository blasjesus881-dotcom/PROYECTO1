package com.example.practica1.dto;

public class StockUpdateDTO {
    private Double stocProd;   // nombre igual que BD si quieres
    private Integer version;

    public StockUpdateDTO() {}

    public Double getStocProd() { return stocProd; }
    public void setStocProd(Double stocProd) { this.stocProd = stocProd; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}
