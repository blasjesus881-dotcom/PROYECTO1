package com.example.practica1.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codiClie")
    private int codiClie;

    @Column(name = "nombClie")
    private String nombClie;

    // Getters y Setters
    public int getCodiClie() {
        return codiClie;
    }

    public void setCodiClie(int codiClie) {
        this.codiClie = codiClie;
    }

    public String getNombClie() {
        return nombClie;
    }

    public void setNombClie(String nombClie) {
        this.nombClie = nombClie;
    }
}