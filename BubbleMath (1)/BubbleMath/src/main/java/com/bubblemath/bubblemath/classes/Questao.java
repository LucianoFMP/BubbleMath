package com.bubblemath.bubblemath.classes;

import java.util.Random;

public class Questao {
    private Bolha bolha1;
    private Bolha bolha2;
    private Bolha bolha3;
    private Bolha bolha4;
    private int erros;

    public Questao(Bolha bolha1, Bolha bolha2, Bolha bolha3, Bolha bolha4) {
        this.bolha1 = bolha1;
        this.bolha2 = bolha2;
        this.bolha3 = bolha3;
        this.bolha4 = bolha4;
        this.erros = 0;
    }

    public Bolha getBolha1() {
        return bolha1;
    }

    public void setBolha1(Bolha bolha1) {
        this.bolha1 = bolha1;
    }

    public Bolha getBolha2() {
        return bolha2;
    }

    public void setBolha2(Bolha bolha2) {
        this.bolha2 = bolha2;
    }

    public Bolha getBolha3() {
        return bolha3;
    }

    public void setBolha3(Bolha bolha3) {
        this.bolha3 = bolha3;
    }

    public Bolha getBolha4() {
        return bolha4;
    }

    public void setBolha4(Bolha bolha4) {
        this.bolha4 = bolha4;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

    public static Questao gerarQuestao(Jogo j) {
        Random r = new Random();
        int max, min, b1, b2, b3, b4;
        if (j.getNivel() > 1) {
            max = 19; min = -9;
        } else {
            max = 9; min = 1;
        }
        while (true) {
            b1 = 0; b2 = 0; b3 = 0; b4 = 0;
            b1 = gerarNumero(r,max,min,b1);
            b2 = gerarNumero(r,max,min,b2);
            b3 = gerarNumero(r,max,min,b3);
            if (j.getNivel() > 0) {
                b4 = gerarNumero(r,max,min,b4);
            }
            if (b1 + b2 == 10 || b1 + b3 == 10 || b1 + b4 == 10 || b2 + b3 == 10 || b2 + b4 == 10 || b3 + b4 == 10 || b1 + b2 + b3 == 10 || b1 + b2 + b4 == 10 || b2 + b3 + b4 == 10 || b1 + b2 + b3 + b4 == 10) {
                break;
            }
        }
        return new Questao(new Bolha(b1), new Bolha(b2), new Bolha(b3), new Bolha(b4));
    }

    public static int gerarNumero(Random r, int max, int min, int bolha){
        while (bolha == 0) {
            bolha = r.nextInt(max) + min;
        }
        return bolha;
    }

    public int verificarSoma(Bolha bolha, Questao questao) {
        bolha.estourar();
        int soma = 0;
        if (questao.bolha1.getEstourado()) {
            soma = soma + questao.bolha1.getValor();
        }
        if (questao.bolha2.getEstourado()) {
            soma = soma + questao.bolha2.getValor();
        }
        if (questao.bolha3.getEstourado()) {
            soma = soma + questao.bolha3.getValor();
        }
        if (questao.bolha4.getEstourado()) {
            soma = soma + questao.bolha4.getValor();
        }
        return verificarResposta(soma, questao);
    }

    public int verificarResposta(int soma, Questao questao) {
        if (soma == 10) {
            return 2;
        } else if (soma > 10) {
            questao.setErros((questao.getErros())+1);
            return 1;
        } else {
            if (questao.getBolha1().getEstourado() && questao.getBolha2().getEstourado() && questao.getBolha3().getEstourado() && questao.getBolha4().getEstourado()){
                questao.setErros((questao.getErros())+1);
                return 1;
            }
            return 0;
        }
    }
}