package com.bubblemath.bubblemath.model;

public class  Bolha {
    private int valor;
    private boolean estourado;

    public Bolha(int valor) {
        this.estourado = false;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public boolean getEstourado() {
        return estourado;
    }

    public void setEstourado(Boolean estourado) { this.estourado = estourado;}

    public void estourar() {
        estourado = true;
    }
}