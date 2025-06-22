package com.bubblemath.bubblemath.classes;

import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ranking implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final String arquivo = new File("rankings.dat").getAbsolutePath();
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

    public static void mostrarRanking(Label[] lista_ranks, List<Ranking> rankings) {
        rankings.sort((r1, r2) -> Integer.compare(r2.getPontuacao(), r1.getPontuacao())); // Ordem decrescente

        for (int i = 0; i < 10; i++) {
            int labelIndex = i * 2;
            if (i < rankings.size()) {
                Ranking r = rankings.get(i);
                lista_ranks[labelIndex].setText(r.getIniciais());
                lista_ranks[labelIndex + 1].setText(Integer.toString(r.getPontuacao()));
            } else {
                lista_ranks[labelIndex].setText("---");
                lista_ranks[labelIndex + 1].setText("---");
            }
        }
    }

    public static void colocarIniciais(String iniciais, List<Ranking> rankings, Jogo jogo) {
        Ranking rank = new Ranking(iniciais, jogo.getPontuacao());
        rankings.add(rank);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rankings.dat"))) {
            oos.writeObject(rankings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void salvarRanking(List<Ranking> rankings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rankings.dat"))) {
            oos.writeObject(rankings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static List<Ranking> carregarRankings() {
        File ranks = new File("rankings.dat");
        if (!ranks.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rankings.dat"))) {
            return (List<Ranking>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
