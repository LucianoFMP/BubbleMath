package com.bubblemath.bubblemath.model;

public class Ranking {
    private String iniciais;
    private int pontuacao;

    public Ranking(String iniciais, int pontuacao) {
        this.iniciais = iniciais;
        this.pontuacao = pontuacao;
    }

    public String getIniciais() {
        return iniciais;
    }

    public void setIniciais(String iniciais) {
        this.iniciais = iniciais;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String mostrarRanking() {
        return "";
    }

    public String colocarIniciais(String iniciais) {
        return "";
    }
}
