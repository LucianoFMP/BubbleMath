package com.bubblemath.bubblemath.model;

import com.bubblemath.bubblemath.BubbleMathController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Jogo {
    private int pontuacao;
    private int nivel;
    private int erros;

    public Jogo() {
        this.nivel = 0;
        this.pontuacao = 0;
        this.erros = 0;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

    public void mostrarIntrucao() {}

    public static Jogo iniciarJogo() {
        return new Jogo();
    }

    public void verificarPontuacao() {
        switch (nivel) {
            case 0:
                pontuacao++;
                break;
            case 1:
                pontuacao = pontuacao + 2;
                break;
            case 2:
                pontuacao = pontuacao + 3;
                break;
        }
    }

    public void proximoNivel() {
        if (pontuacao < 10) {
            setNivel(0);
        } else if (pontuacao < 30) {
            setNivel(1);
        } else {
            setNivel(2);
        }
    }

    public int[] finalizarJogo() {
        int e = getErros();
        int p = getPontuacao();
        int a = 0;
        switch (getNivel()) {
            case 0:
                a = getPontuacao();
                break;
            case 1:
                a = 10 + ((getPontuacao()-10)/2);
                break;
            case 2:
                a = 30+((getPontuacao()-30)/3);
                break;
        }
        return new int[]{e, p, a};
    }
}
