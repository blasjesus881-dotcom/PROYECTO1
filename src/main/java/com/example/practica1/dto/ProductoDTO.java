package com.example.practica1.dto;

public class ProductoDTO {
    private Integer codiProd;
    private String nombProd;
    private Double precProd;
    private Double stocProd;
    private Integer version; // para control optimista (opcional en creación)

    public ProductoDTO() {}

    public Integer getCodiProd() { return codiProd; }
    public void setCodiProd(Integer codiProd) { this.codiProd = codiProd; }

    public String getNombProd() { return nombProd; }
    public void setNombProd(String nombProd) { this.nombProd = nombProd; }

    public Double getPrecProd() { return precProd; }
    public void setPrecProd(Double precProd) { this.precProd = precProd; }

    public Double getStocProd() { return stocProd; }
    public void setStocProd(Double stocProd) { this.stocProd = stocProd; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}
