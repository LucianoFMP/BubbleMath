package com.bubblemath.bubblemath;

import com.bubblemath.bubblemath.classes.Ranking;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bubblemath.bubblemath.classes.Jogo;
import com.bubblemath.bubblemath.classes.Bolha;
import com.bubblemath.bubblemath.classes.Questao;
import javafx.util.Duration;


public class BubbleMathMain {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Jogo jogo;
    private Questao questao;
    private List<Ranking> rankings = Ranking.carregarRankings();

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

    @FXML
    private Button iniciarTutorial;
    @FXML
    private ImageView tutorial;
    @FXML
    private Pane fundoAzul;

    @FXML
    private Button salvarPontuacao;
    @FXML
    private ImageView menuSalvarRank;
    @FXML
    private Button salvarRank;
    @FXML
    private TextField iniciaisInput;


    @FXML
    private Label r1;
    @FXML
    private Label p1;
    @FXML
    private Label r2;
    @FXML
    private Label p2;
    @FXML
    private Label r3;
    @FXML
    private Label p3;
    @FXML
    private Label r4;
    @FXML
    private Label p4;
    @FXML
    private Label r5;
    @FXML
    private Label p5;
    @FXML
    private Label r6;
    @FXML
    private Label p6;
    @FXML
    private Label r7;
    @FXML
    private Label p7;
    @FXML
    private Label r8;
    @FXML
    private Label p8;
    @FXML
    private Label r9;
    @FXML
    private Label p9;
    @FXML
    private Label r10;
    @FXML
    private Label p10;

    public void mostrarIntrucao() {
        Jogo.mostrarIntrucao(fundoAzul, iniciarTutorial, tutorial);
    }

    public void iniciarJogo(ActionEvent event) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("jogoView.fxml"));
        root = loader.load();
        BubbleMathMain controller = loader.getController();

        controller.jogo = Jogo.iniciarJogo();
        controller.questao = Questao.gerarQuestao(controller.jogo);
        controller.atualizarBolhas();
        controller.cronometro();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void atualizarBolhas() throws InterruptedException {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));

        Image b1 = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/numeros/"+numeros[questao.getBolha1().getValor()+8]));
        Image estouro = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/estouro.png"));
        Image bolha = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/bolha.png"));
        bolha_num1.setImage(b1);
        if (questao.getBolha1().getEstourado()) {
            bolha1.setOpacity(1);
            bolha1.setImage(estouro);
            pause.setOnFinished(e -> bolha1.setVisible(false));
            pause.play();
        } else {
            bolha1.setOpacity(0.54);
            bolha1.setImage(bolha);
            bolha1.setVisible(true);
        }
        botao1.setUserData(questao.getBolha1());

        Image b2 = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/numeros/"+numeros[questao.getBolha2().getValor()+8]));
        bolha_num2.setImage(b2);
        if (questao.getBolha2().getEstourado()) {
            bolha2.setOpacity(1);
            bolha2.setImage(estouro);
            pause.setOnFinished(e -> bolha2.setVisible(false));
            pause.play();
        } else {
            bolha2.setOpacity(0.54);
            bolha2.setImage(bolha);
            bolha2.setVisible(true);
        }
        botao2.setUserData(questao.getBolha2());

        Image b3 = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/numeros/"+numeros[questao.getBolha3().getValor()+8]));
        bolha_num3.setImage(b3);
        if (questao.getBolha3().getEstourado()) {
            bolha3.setOpacity(1);
            bolha3.setImage(estouro);
            pause.setOnFinished(e -> bolha3.setVisible(false));
            pause.play();
        } else {
            bolha3.setOpacity(0.54);
            bolha3.setImage(bolha);
            bolha3.setVisible(true);
        }
        botao3.setUserData(questao.getBolha3());

        if (questao.getBolha4().getValor() != 0) {
            Image b4 = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/numeros/"+numeros[questao.getBolha4().getValor()+8]));
            bolha_num4.setImage(b4);
            if (questao.getBolha4().getEstourado()) {
                bolha4.setOpacity(1);
                bolha4.setImage(estouro);
                pause.setOnFinished(e -> bolha4.setVisible(false));
                pause.play();
            } else {
                bolha4.setOpacity(0.54);
                bolha4.setImage(bolha);
                bolha4.setVisible(true);
            }
            botao4.setUserData(questao.getBolha4());
        }
    }

    public void estourarBolha(ActionEvent event) throws InterruptedException {
        Button botaoBolha = (Button) event.getSource();
        Bolha bolha = (Bolha) botaoBolha.getUserData();
        int resposta = questao.verificarSoma(bolha, questao);
        if (resposta == 2) {
            URL som = getClass().getResource("/com/bubblemath/bubblemath/sons/certo.mp3");
            AudioClip audioClip = new AudioClip(som.toString());
            audioClip.play();
            jogo.verificarPontuacao();
            jogo.proximoNivel();
            if (jogo.getNivel() > 0) {
                proximoNivel();
            }
            questao = Questao.gerarQuestao(jogo);
            atualizarBolhas();
            pontuacao.setText(Integer.toString(jogo.getPontuacao()));
        } else if (resposta == 1) {
            URL som = getClass().getResource("/com/bubblemath/bubblemath/sons/errado.mp3");
            AudioClip audioClip = new AudioClip(som.toString());
            audioClip.play();
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
        BubbleMathMain controller = loader.getController();

        controller.setJogo(this.jogo);
        int[] f = controller.jogo.finalizarJogo();
        controller.erros.setText(Integer.toString(f[0])+" erros");
        controller.pontuacao.setText(Integer.toString( f[1])+" pontos");
        controller.acertos.setText(Integer.toString(f[2])+" acertos");
        controller.exibirEstrelas();
        controller.rankings = Ranking.carregarRankings();
        if (controller.rankings.size() < 10) {
            controller.salvarPontuacao.setVisible(true);
        } else {
            Ranking piorRanking = rankings.stream()
                    .min((r1, r2) -> Integer.compare(r1.getPontuacao(), r2.getPontuacao()))
                    .orElse(null);
            if (piorRanking != null && controller.jogo.getPontuacao() > piorRanking.getPontuacao()) {
                controller.salvarPontuacao.setVisible(true);
            }
        }

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
        BubbleMathMain controller = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void proximoNivel() {
        Image novoFundo = new Image(getClass().getResourceAsStream("/com/bubblemath/bubblemath/img/jogoFundoPlus.png"));
        fundoJogo.setImage(novoFundo);
        bolha4.setVisible(true);
        bolha_num4.setVisible(true);
        botao4.setVisible(true);
    }

    public void fecharInstrucao() {
        fundoAzul.setVisible(false);
        iniciarTutorial.setVisible(false);
        tutorial.setVisible(false);
    }

    public void menuSalvarRanking() {
        menuSalvarRank.setVisible(true);
        salvarRank.setVisible(true);
        iniciaisInput.setVisible(true);
        fundoAzul.setVisible(true);
    }

    public void salvarRanking() throws IOException {
        String iniciais = iniciaisInput.getText();
        if (iniciais.matches("[a-zA-Z]{0,3}")) {
            if (rankings.size() < 10) {
                Ranking.colocarIniciais(iniciais, rankings, jogo);
            } else {
                Ranking piorRanking = rankings.stream()
                        .min((r1, r2) -> Integer.compare(r1.getPontuacao(), r2.getPontuacao()))
                        .orElse(null);
                if (piorRanking != null && jogo.getPontuacao() > piorRanking.getPontuacao()) {
                    rankings.remove(piorRanking);
                    Ranking.colocarIniciais(iniciais, rankings, jogo);
                }
            }
            irRankings();
        }
    }

    public void irRankings() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rankingView.fxml"));
        root = loader.load();
        BubbleMathMain controller = loader.getController();
        controller.rankings = Ranking.carregarRankings();
        Ranking.mostrarRanking(controller.getListaRank(), controller.rankings);

        Stage stage = (Stage) pontuacao.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Label[] getListaRank() {
        return new Label[]{r1, p1, r2, p2, r3, p3, r4, p4, r5, p5,
                r6, p6, r7, p7, r8, p8, r9, p9, r10, p10};
    }
}