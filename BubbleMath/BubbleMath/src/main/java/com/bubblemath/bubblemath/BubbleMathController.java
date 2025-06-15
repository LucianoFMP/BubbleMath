package com.bubblemath.bubblemath;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bubblemath.bubblemath.model.Jogo;
import com.bubblemath.bubblemath.model.Bolha;
import com.bubblemath.bubblemath.model.Questao;
import com.bubblemath.bubblemath.model.Ranking;


public class BubbleMathController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Jogo jogo;
    private Questao questao;

    private int segundos;
    private final String[] numeros = {"menosum.png","menosdois.png","menostres.png","menosquatro.png","menoscinco.png","menosseis.png","menossete.png","menosoito.png","menosnove.png","um.png","dois.png","tres.png","quatro.png","cinco.png","seis.png", "sete.png","oito.png","nove.png"};

    @FXML
    private Button botao1;

    @FXML
    private Button botao2;

    @FXML
    private Button botao3;

    @FXML
    private Button botao4;

    @FXML
    private Label pontuacao;

    @FXML
    private Label cronometro;

    @FXML
    private Label somas;

    @FXML
    private ImageView fundoJogo;

    @FXML
    private Label acertos;

    @FXML
    private Label erros;

    @FXML
    private ImageView estrela1;

    @FXML
    private ImageView estrela2;

    @FXML
    private ImageView estrela3;

    @FXML
    private ImageView estrela4;

    @FXML
    private ImageView estrela5;

    @FXML
    private ImageView bolha1;

    @FXML
    private ImageView bolha2;

    @FXML
    private ImageView bolha3;

    @FXML
    private ImageView bolha4;

    @FXML
    private ImageView bolha_num1;
    @FXML
    private ImageView bolha_num2;
    @FXML
    private ImageView bolha_num3;
    @FXML
    private ImageView bolha_num4;

    public void iniciarJogo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("jogoView.fxml"));
        root = loader.load();
        BubbleMathController controller = loader.getController();

        controller.jogo = Jogo.iniciarJogo();
        controller.questao = Questao.gerarQuestao(controller.jogo);
        controller.atualizarBolhas();
        controller.cronometro();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void atualizarBolhas() {
        Image b1 = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/numeros/"+numeros[questao.getBolha1().getValor()+8]));
        bolha_num1.setImage(b1);
        if (questao.getBolha1().getEstourado()) {
            bolha1.setVisible(false);
        } else {
            bolha1.setVisible(true);
        }
        botao1.setUserData(questao.getBolha1());

        Image b2 = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/numeros/"+numeros[questao.getBolha2().getValor()+8]));
        bolha_num2.setImage(b2);
        if (questao.getBolha2().getEstourado()) {
            bolha2.setVisible(false);
        } else {
            bolha2.setVisible(true);
        }
        botao2.setUserData(questao.getBolha2());

        Image b3 = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/numeros/"+numeros[questao.getBolha3().getValor()+8]));
        bolha_num3.setImage(b3);
        if (questao.getBolha3().getEstourado()) {
            bolha3.setVisible(false);
        } else {
            bolha3.setVisible(true);
        }
        botao3.setUserData(questao.getBolha3());

        if (questao.getBolha4().getValor() != 0) {
            Image b4 = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/numeros/"+numeros[questao.getBolha4().getValor()+8]));
            bolha_num4.setImage(b4);
            if (questao.getBolha4().getEstourado()) {
                bolha4.setVisible(false);
            } else {
                bolha4.setVisible(true);
            }
            botao4.setUserData(questao.getBolha4());
        }
    }

    public void estourarBolha(ActionEvent event) {
        Button botaoBolha = (Button) event.getSource();
        Bolha bolha = (Bolha) botaoBolha.getUserData();
        int resposta = questao.verificarSoma(bolha, questao);
        if (resposta == 2) {
            jogo.verificarPontuacao();
            jogo.proximoNivel();
            if (jogo.getNivel() > 0) {
                proximoNivel();
            }
            questao = Questao.gerarQuestao(jogo);
            atualizarBolhas();
            pontuacao.setText(Integer.toString(jogo.getPontuacao()));
        } else if (resposta == 1) {
            if (questao.getErros() == 1) {
                questao.getBolha1().setEstourado(false);
                questao.getBolha2().setEstourado(false);
                questao.getBolha3().setEstourado(false);
                questao.getBolha4().setEstourado(false);
                jogo.setErros(jogo.getErros() + 1);
            } else if (questao.getErros() == 2) {
                questao = Questao.gerarQuestao(jogo);
                jogo.setErros(jogo.getErros() + 1);
                atualizarBolhas();
            }
        }
        somas.setText(atualizarSomas());
        atualizarBolhas();
    }

    public void cronometro() {
        segundos = 60;
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(() -> {
            segundos--;
            Platform.runLater(() -> {
                cronometro.setText(Integer.toString(segundos));
            });
            if (segundos == 0) {
                timer.shutdown();
                Platform.runLater(() -> {
                    try {
                        finalizar();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private String atualizarSomas(){
        String somas = "";
        int aux = 0;
        Bolha bolhas[] = new Bolha[4];
        bolhas[0] = questao.getBolha1();
        bolhas[1] = questao.getBolha2();
        bolhas[2] = questao.getBolha3();
        bolhas[3] = questao.getBolha4();
        for (Bolha b: bolhas) {
            if (b.getEstourado() && aux > 0) {
                somas = somas + " + " + b.getValor();
                aux = aux + b.getValor();
            } else if (b.getEstourado()) {
                somas = somas + b.getValor();
                aux = aux + b.getValor();
            }
        }
        if (aux  == 0) {
            somas = "0";
        } else {
            somas = somas + " = " + aux;
        }
        return somas;
    }

    private void finalizar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("finalView.fxml"));
        root = loader.load();
        BubbleMathController controller = loader.getController();

        controller.setJogo(this.jogo);
        int[] f = controller.jogo.finalizarJogo();
        controller.erros.setText(Integer.toString(f[0])+" erros");
        controller.pontuacao.setText(Integer.toString( f[1])+" pontos");
        controller.acertos.setText(Integer.toString(f[2])+" acertos");
        controller.exibirEstrelas();

        Stage stage = (Stage) pontuacao.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public void exibirEstrelas() {
        int aux = 10;
        ImageView[] estrelas = {estrela1,estrela2,estrela3,estrela4,estrela5};
        for (int i = 0; i < 5; i++) {
            if (jogo.getPontuacao() >= aux) {
                estrelas[i].setOpacity(1);
                aux = aux+10;
            } else {
                estrelas[i].setOpacity(0.5);
            }
        }
    }

    public void menuInicial(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenuView.fxml"));
        root = loader.load();
        BubbleMathController controller = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void proximoNivel() {
        Image novoFundo = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/jogoFundoPlus.png"));
        fundoJogo.setImage(novoFundo);
        somas.setTranslateX(420);
        somas.setTranslateY(-155);
        bolha1.setTranslateX(200);
        bolha1.setTranslateY(75);
        bolha_num1.setTranslateX(200);
        bolha_num1.setTranslateY(75);
        botao1.setTranslateX(200);
        botao1.setTranslateY(75);
        bolha2.setTranslateX(0);
        bolha2.setTranslateY(230);
        bolha_num2.setTranslateX(0);
        bolha_num2.setTranslateY(230);
        botao2.setTranslateX(0);
        botao2.setTranslateY(230);
        bolha3.setTranslateX(-210);
        bolha3.setTranslateY(75);
        bolha_num3.setTranslateX(-210);
        bolha_num3.setTranslateY(75);
        botao3.setTranslateX(-210);
        botao3.setTranslateY(75);
        bolha4.setVisible(true);
        bolha_num4.setVisible(true);
        botao4.setVisible(true);
    }
}