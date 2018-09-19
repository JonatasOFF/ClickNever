package Logica;

import Interfaces.CallBackInterface;
import Models.Mouse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.objects.Global;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @Author: Jonatas De Oliveira Ferreira
 * @Version: 0.5 - SNAPSHOT
 *
 * @CoisasParaFazer:
 *
 *
 * Em Iniciar/Start colocar um CheckBox para conferir se quer executar com o clicker Infinite ou com Tempo
 *
 * -*Tentar dar introdução a <Clicker Never>Utilizando Vbox ou Hbox, para colocar uma lista ou tabela de posições do clicker que serão ociladas conforme
 * Fazer isso até a !versão 0.6 ou 0.7 ou nessa ou na 0.5!
 *
 * </Clicker Never> Isso Em teste claro -*
 * @ATALHOS:
 * @*LD* : Leitura de dados (gravar,ler,resetar)
 *
 *
 */
public class Controlador implements Initializable {

    @FXML
    public TableView<Mouse> tvTabela;
    @FXML
    public TableColumn<Mouse, Integer> coluna1;
    @FXML
    public TableColumn<Mouse, Integer> coluna2;
    @FXML
    public TableColumn<Mouse, Integer> apenas;
    @FXML
    public CheckBox cbDesativarTeclado;

    private ObservableList<Mouse> observableList;

    private CallBackEspera cb = new CallBackEspera();

    private List<Mouse> mouses = new ArrayList<>();

    private int segundos = 0;

    private int minutos = 0;

    private int horas = 0;

    private int vezes = 0;

    private int tempoDeClicker;

    private boolean podeExecutarClicke = true;

    private boolean pausa;

    private boolean abertoTempo;

    private final static String LANGUAGE = System.getProperty("user.language");

    private boolean abertoClicker;

    private boolean jaExecutou = false;

    private boolean pauseExecuter = false;

    private int getTfTempoDeClicker() {
        if (tfTempoDeClicker.getText().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(tfTempoDeClicker.getText());
        }
    }

    private int getVelocidade(String velocidade) {
        switch (velocidade) {
            case ("0.001/s"):
                tempoDeClicker = 1;
                break;
            case ("0.01/s"):
                tempoDeClicker = 10;
                break;
            case ("0.1/s"):
                tempoDeClicker = 100;
                break;
            case ("1/s"):
                tempoDeClicker = 1000;
                break;
        }
        return tempoDeClicker;
    }

    private String comecaEm;

    private String pausaEm;

    private String andamentoEm;

    private String fimDaExecucao;

    private String getListaSave() {
        //hora,minuto,segundo,numeroClicker,tempoComeca,tfStop,pausa/continua,tfStart,one,dez,cem,mil,ctrl,isTempoDeClicker
        return hours.getText() +
                "," +
                min.getText() +
                "," +
                seg.getText() +
                "," +
                tfTempoDeClicker.getText() +
                "," +
                tfStop.getText() +
                "," +
                tfWait.getText() +
                "," +
                tfStart.getText() +
                "," +
                oneMs.isSelected() +
                "," +
                dezms.isSelected() +
                "," +
                cemMs.isSelected() +
                "," +
                milMs.isSelected() +
                "," +
                ctrl.isSelected() +
                "," +
                cbTempoDeClicker.isSelected();
    }

    void setPodeExecutarClicke(boolean podeExecutarClicke) {
        this.podeExecutarClicke = podeExecutarClicke;
    }

    private void setProgressbar(double value) {
        progressbar.indeterminateProperty();
        progressbar.setProgress(value);
    }

    @FXML
    public Label lbDuracao;
    @FXML
    public Label lbStart;
    @FXML
    public Label lbStop;
    @FXML
    public Label lbPause;
    @FXML
    public TextField tfStart;
    @FXML
    public TextField tfWait;
    @FXML
    public CheckBox ctrl;
    @FXML
    public Label lbErrorComeca;
    @FXML
    public Label lbErrorPara;
    @FXML
    public Label lbErrorPausa;
    @FXML
    public CheckBox cbTempoDeClicker;
    @FXML
    public TextField tfTempoDeClicker;
    @FXML
    public Label lbTempoDeClicker;
    @FXML
    public TextField tfStop;
    @FXML
    public Button btAtivaMouseTempo;
    @FXML
    public TextArea textoTempo;
    @FXML
    public Label lbTempo;
    @FXML
    public TextField hours;
    @FXML
    public TextField min;
    @FXML
    public TextField seg;
    @FXML
    private Pane KeyboardMenu;
    @FXML
    private ToggleGroup groupBotoes;
    @FXML
    private RadioButton oneMs;
    @FXML
    private RadioButton dezms;
    @FXML
    private RadioButton cemMs;
    @FXML
    private RadioButton milMs;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private Label iniciando;
    @FXML
    private Pane MouseControler;
    @FXML
    private Pane panelClicker;
    @FXML
    public TextArea textoClicker;
    @FXML
    public Label lbClicke;

    @FXML
    private void avisoVelocidade() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.show();
        switch (LANGUAGE) {
            case ("pt"):
                alert.setTitle("Aviso");
                alert.setContentText("Esta opção pode levar a uma perda de Clicker");
                alert.setHeaderText("Aviso");
                break;
            case ("fr"):
                alert.setTitle("Avis");
                alert.setContentText("Cette option peut entraîner une perte de clics");
                alert.setHeaderText("Avis");
                break;
            default:
                alert.setTitle("Notice");
                alert.setContentText("This opção can lead to a clickthrough");
                alert.setHeaderText("Notice");
        }
    }

    @FXML
    private void irApaginaInicial() {
        MouseControler.setVisible(false);
        KeyboardMenu.setVisible(false);
        panelClicker.setVisible(true);
    }

    /**
     * Projeto em Alpha
     * Serve para automatizar qualquer Game Clicker
     */
    @FXML
    void irMouseControler() {
        panelClicker.setVisible(false);
        MouseControler.setVisible(true);
        KeyboardMenu.setVisible(false);
    }

    @FXML
    private void irKeyboard() {
        panelClicker.setVisible(false);
        MouseControler.setVisible(false);
        KeyboardMenu.setVisible(true);
    }

    @FXML
    public void informacoesTempo() {
        if (!abertoTempo) {
            abertoTempo = true;
            lbTempo.setVisible(true);
            textoTempo.setVisible(true);
        } else {
            lbTempo.setVisible(false);
            textoTempo.setVisible(false);
            abertoTempo = false;
        }
    }

    @FXML
    public void InformacoesClicke() {
        if (!abertoClicker) {
            abertoClicker = true;
            textoClicker.setVisible(true);
            lbClicke.setVisible(true);
        } else {
            abertoClicker = false;
            textoClicker.setVisible(false);
            lbClicke.setVisible(false);
        }
    }

    @FXML
    public void informacaoReseta() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.show();
        switch (LANGUAGE) {
            case ("pt"):
                alert.setTitle("Reset");
                alert.setContentText("Lembre-se se. Todos os dados salvos serão redefinidos");
                alert.setHeaderText("Aqui você reseta todas as suas informações");
                break;
            case ("fr"):
                alert.setTitle("Reset");
                alert.setContentText("Rappelez-vous Toutes les données enregistrées seront réinitialisées");
                alert.setHeaderText("Ici vous redimensionnez toutes vos informations");
                break;
            default:
                alert.setTitle("Reset");
                alert.setContentText("Remember if. All saved data will be reset");
                alert.setHeaderText("Here you resize all your information");
                break;
        }
    }

    private void atualizaTeclasMostradas() {
        if (ctrl.isSelected()) {
            if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                lbStart.setText("Ctrl + " + tfStart.getText());
                lbStop.setText("Ctrl + " + tfStop.getText());
                lbPause.setText("Ctrl + " + tfWait.getText());
            }
        } else {
            if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                lbStart.setText(tfStart.getText());
                lbStop.setText(tfStop.getText());
                lbPause.setText(tfWait.getText());
            }
        }
    }

    //<LEITURA DE DADOS>*LD*

    @FXML
    void limparDados() {
        hours.setText("");
        min.setText("");
        seg.setText("");
        tfTempoDeClicker.setText("5");
        tfStop.setText("Z");
        tfWait.setText("X");
        tfStart.setText("S");
        oneMs.setSelected(false);
        dezms.setSelected(false);
        cemMs.setSelected(false);
        milMs.setSelected(true);
        ctrl.setSelected(true);
        cbTempoDeClicker.setSelected(true);
        atualizaTeclasMostradas();
    }

    private void lerDados() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\" + "Saves" + "\\" + "save.ini")))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                System.out.println(Arrays.toString(dados));
                hours.setText(dados[0]);
                min.setText(dados[1]);
                seg.setText(dados[2]);
                tfTempoDeClicker.setText(dados[3]);
                tfStop.setText(dados[4]);
                tfWait.setText(dados[5]);
                tfStart.setText(dados[6]);
                oneMs.setSelected(Boolean.parseBoolean(dados[7]));
                dezms.setSelected(Boolean.parseBoolean(dados[8]));
                cemMs.setSelected(Boolean.parseBoolean(dados[9]));
                milMs.setSelected(Boolean.parseBoolean(dados[10]));
                ctrl.setSelected(Boolean.parseBoolean(dados[11]));
                cbTempoDeClicker.setSelected(Boolean.parseBoolean(dados[12]));
            }
        } catch (IOException ignored) {
        }
    }

    private void armazenaDados() {

        try {
            File save = new File(String.valueOf(Files.createDirectories(Paths.get(System.getProperty("user.dir") + "\\" + "Saves"))));
            FileWriter whiter = new FileWriter(save + "\\" + "save.ini");

            whiter.append(getListaSave());

            whiter.flush();
            whiter.close();
        } catch (IOException ignored) {
        }
    }

    //<\LEITURA DE DADOS>*LD*

    private int velocidadeTempo(String velocidade) {
        switch (velocidade) {
            case ("0.001/s"):
                return 1000;
            case ("0.01/s"):
                return 100;
            case ("0.1/s"):
                return 10;
            case ("1/s"):
                return 1;
            default:
                return 1;
        }
    }

    private void pegaValores() {

        if (!(seg.getText().equals(""))) {
            segundos = Integer.parseInt(seg.getText());
        }
        if (!(min.getText().equals(""))) {
            minutos = Integer.parseInt(min.getText());
        }
        if (!(hours.getText().equals(""))) {
            if (horas > 24) {
                horas = 24;
            } else {
                horas = Integer.parseInt(hours.getText());
            }
        }
    }

    private void tempo(int tempo) {
        int h = tempo / 3600;
        int m = (tempo % 3600) / 60;
        int s = (tempo % 3600) % 60;
        Platform.runLater(() -> lbDuracao.setText(h + " : " + m + " : " + s));
    }

    private void pauseClicker() throws InterruptedException {
        if (pausa) {
            Thread.sleep(3000);
            if (!podeExecutarClicke) {
                pausa = false;
                Platform.runLater(() -> iniciando.setText(andamentoEm));
            } else {
                pauseClicker();
            }
        }
    }

    private void alertKeyboard() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.show();
        System.out.println(LANGUAGE);
        switch (LANGUAGE) {
            case ("pt"):
                alert.setTitle("Error");
                alert.setContentText("Por favor acesse Opções -> Teclado, e Resolva Erros");
                alert.setHeaderText("Existem letras iguais ou faltam letras");
                break;
            case ("fr"):
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez accéder aux Options -> Clavier et Résoudre les erreurs");
                alert.setHeaderText("Il y a des lettres égales ou manquantes");
                break;
            default:
                alert.setTitle("Error");
                alert.setContentText("Please access Options -> Keyboard, and Resolve Errors");
                alert.setHeaderText("There are equal or missing letters");
        }
        jaExecutou = false;
    }

    private void manipulandoTabela() {

        coluna1.setCellValueFactory(new PropertyValueFactory<>("x"));
        coluna2.setCellValueFactory(new PropertyValueFactory<>("y"));
        apenas.setCellValueFactory(new PropertyValueFactory<>("clickers"));

        Mouse c1 = new Mouse(2, 3, 4, 5);
        Mouse c2 = new Mouse(2, 2, 2, 2);
        mouses.add(c1);
        mouses.add(c2);
        observableList = FXCollections.observableArrayList(mouses);
        tvTabela.setItems(observableList);
        System.out.println(tvTabela.getItems().toString());

    }

    @FXML
    private void executaMouseTempo() {
        try {
            if (!jaExecutou) {
                jaExecutou = true;
                if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                    pegaValores();
                    vezes = 0;
                    int minTrue = minutos * 60;
                    int hours = horas * 3600;
                    vezes = (segundos + minTrue + hours);
                    cb.espera(() -> {
                        try {
                            Platform.runLater(() -> iniciando.setText(andamentoEm));
                            executaclicker(vezes);
                            jaExecutou = false;
                        } catch (AWTException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    alertKeyboard();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            jaExecutou = false;
        }
    }

    /**
     * @param quantas Pega valor de Quantos Clicker a serem executados
     * @throws AWTException         null
     * @throws InterruptedException null
     */
    private void executaclicker(double quantas) throws AWTException, InterruptedException {
        if (quantas != 0) {
            pauseExecuter = true;
            setPodeExecutarClicke(true);
            Robot robot = new Robot();
            RadioButton radio = (RadioButton) groupBotoes.getSelectedToggle();
            quantas *= velocidadeTempo(radio.getText());
            for (double i = 0; i <= quantas && podeExecutarClicke; i++) {
                radio = (RadioButton) groupBotoes.getSelectedToggle();
                tempo((int) (quantas - i) / velocidadeTempo(radio.getText()));
                double result = i / quantas;

                //interface que faz aparecer
                setProgressbar(result);

                robot.delay(getVelocidade(radio.getText()));
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

                pauseClicker();
            }
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
                System.out.println(LANGUAGE);
                switch (LANGUAGE) {
                    case ("pt"):
                        alert.setTitle("Error");
                        alert.setContentText("Não a números a serem executados");
                        break;
                    case ("fr"):
                        alert.setTitle("Erreur");
                        alert.setContentText("Pas les nombres à courir");
                        break;
                    default:
                        alert.setTitle("Error");
                        alert.setContentText("Not the numbers to run");
                }
            });
        }
        Platform.runLater(() -> {
            iniciando.setText(fimDaExecucao);
            lbDuracao.setText("0 : 0 : 0");
            progressbar.setProgress(0);
            progressbar.indeterminateProperty();
        });
        Thread.sleep(1000);
        Platform.runLater(() -> {
            iniciando.setVisible(false);
            pauseExecuter = false;
        });
    }

    @FXML
    public void clickerInfinite() {
        if (!jaExecutou) {
            if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                jaExecutou = true;
                cb.espera(() -> {
                    Platform.runLater(() -> iniciando.setText("Em andamento..."));
                    do {
                        Robot robotl = new Robot();
                        RadioButton radio = (RadioButton) groupBotoes.getSelectedToggle();
                        robotl.mousePress(InputEvent.BUTTON1_MASK);
                        robotl.mouseRelease(InputEvent.BUTTON1_MASK);
                        robotl.delay(getVelocidade(radio.getText()));
                        //robotl.waitForIdle();
                    } while (podeExecutarClicke);
                    jaExecutou = false;
                    setPodeExecutarClicke(true);
                    Platform.runLater(() -> iniciando.setText(fimDaExecucao));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> iniciando.setVisible(false));
                });
            } else {
                alertKeyboard();
            }
        }
    }

    @FXML
    public void stopClickerInfinite() {
        setPodeExecutarClicke(false);
    }

    private void verificaTempoDeClicker() {
        if (cbTempoDeClicker.isSelected()) {
            tfTempoDeClicker.setOpacity(1);
            tfTempoDeClicker.setEditable(true);
            lbTempoDeClicker.setOpacity(1);
        } else {
            tfTempoDeClicker.setOpacity(0.50);
            tfTempoDeClicker.setEditable(false);
            lbTempoDeClicker.setOpacity(0.50);
        }
    }

    @FXML
    public void InformacoesDesativarTeclado() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.show();
        switch (LANGUAGE) {
            case ("pt"):
                alert.setTitle("KeyBoard");
                alert.setHeaderText("Aqui você desativa a resposta do teclado");
                alert.setContentText("Comandos do teclado não respondem a essa opção desativada");
                break;
            case ("fr"):
                alert.setTitle("KeyBoard");
                alert.setContentText("Les commandes clavier ne répondent pas à cette option handicapé");
                alert.setHeaderText("Ici, vous désactivez la réponse au clavier");
                break;
            default:
                alert.setTitle("KeyBoard");
                alert.setContentText("Keyboard commands do not respond to this option disable");
                alert.setHeaderText("Here you deactivate the keyboard response");
                break;
        }
    }

    /**
     * Detectar/Funcionar/Programa Aberto
     * A classe Detecta serve para ver se existe algum valor errado
     * Também funciona como a classe que lança coisas a se fazer enquanto
     * o programa está aberto
     */
    public class detecta {

        /**
         * Pedir ajuda por irmão pra ver se tem uma maneira melhor de fazer isso
         */
        void retiraNumerosClicker() {
            hours.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    hours.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.matches("\\d\\d\\d")) {
                    hours.setText(hours.getText(0, 2) + "");
                }
                armazenaDados();
            });
            min.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    min.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.matches("\\d\\d\\d")) {
                    min.setText(min.getText(0, 2) + "");
                }
                armazenaDados();
            });
            seg.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    seg.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.matches("\\d\\d\\d")) {
                    seg.setText(seg.getText(0, 2) + "");
                }
                armazenaDados();
            });
            tfStop.textProperty().addListener((observable, oldValue, newValue) -> {
                tfStop.setText(tfStop.getText().toUpperCase());
                try {
                    if (!newValue.matches("\\w")) {
                        tfStop.setText(tfStop.getText(1, 2));
                    }
                } catch (Exception ignore) {
                }
                if (tfStop.getText().equals(tfStart.getText())) {
                    lbErrorPara.setVisible(true);
                }
                if (tfStop.getText().equals(tfWait.getText())) {
                    lbErrorPara.setVisible(true);
                }
                if (!(tfStop.getText().equals(tfWait.getText()) || tfStop.getText().equals(tfStart.getText()))) {
                    lbErrorPara.setVisible(false);
                }
                if (!(tfStop.getText().equals(tfWait.getText()) || tfWait.getText().equals(tfStart.getText()))) {
                    lbErrorPausa.setVisible(false);
                } else {
                    lbErrorPausa.setVisible(true);
                }
                if (!(tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                    lbErrorComeca.setVisible(false);
                } else {
                    lbErrorComeca.setVisible(true);
                }
                if (tfStop.getText().equals("")) {
                    lbErrorPausa.setVisible(true);
                }
                armazenaDados();
                atualizaTeclasMostradas();
            });
            tfStart.textProperty().addListener((observable, oldValue, newValue) -> {
                tfStart.setText(tfStart.getText().toUpperCase());
                try {
                    if (!newValue.matches("\\w")) {
                        tfStart.setText(tfStart.getText(1, 2));
                    }
                } catch (Exception ignore) {
                }
                if (tfStart.getText().equals(tfStop.getText())) {
                    lbErrorComeca.setVisible(true);
                }
                if (tfStart.getText().equals(tfWait.getText())) {
                    lbErrorComeca.setVisible(true);
                }
                if (!(tfStart.getText().equals(tfWait.getText()) || tfStart.getText().equals(tfStop.getText()))) {
                    lbErrorComeca.setVisible(false);
                }
                if (!(tfStart.getText().equals(tfWait.getText()) || tfWait.getText().equals(tfStop.getText()))) {
                    lbErrorPausa.setVisible(false);
                } else {
                    lbErrorPausa.setVisible(true);
                }
                if (!(tfStart.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfWait.getText()))) {
                    lbErrorPara.setVisible(false);
                } else {
                    lbErrorPara.setVisible(true);
                }
                if (tfStart.getText().equals("")) {
                    lbErrorPausa.setVisible(true);
                }
                armazenaDados();
                atualizaTeclasMostradas();
            });
            tfWait.textProperty().addListener((observable, oldValue, newValue) -> {
                tfWait.setText(tfWait.getText().toUpperCase());
                try {
                    if (!newValue.matches("\\w")) {
                        tfWait.setText(tfWait.getText(1, 2));
                    }
                } catch (Exception ignore) {
                }
                if (tfWait.getText().equals(tfStop.getText())) {
                    lbErrorPausa.setVisible(true);
                }
                if (tfWait.getText().equals(tfStart.getText())) {
                    lbErrorPausa.setVisible(true);
                }
                if (!(tfWait.getText().equals(tfStart.getText()) || tfWait.getText().equals(tfStop.getText()))) {
                    lbErrorPausa.setVisible(false);
                }
                if (!(tfWait.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfStop.getText()))) {
                    lbErrorComeca.setVisible(false);
                } else {
                    lbErrorComeca.setVisible(true);
                }
                if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfWait.getText()))) {
                    lbErrorPara.setVisible(false);
                } else {
                    lbErrorPara.setVisible(true);
                }
                if (tfWait.getText().equals("")) {
                    lbErrorPausa.setVisible(true);
                }
                armazenaDados();
                atualizaTeclasMostradas();
            });
            cbTempoDeClicker.selectedProperty().addListener((observable, oldValue, newValue) -> {
                verificaTempoDeClicker();
                verificaTempoDeClicker();
                armazenaDados();
            });
            cbDesativarTeclado.selectedProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    ativarTeclado(newValue);
                } catch (NativeHookException e) {
                    e.printStackTrace();
                }
            });
            ctrl.selectedProperty().addListener((observable, oldValue, newValue) -> {
                atualizaTeclasMostradas();
                armazenaDados();
            });
            oneMs.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            dezms.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            cemMs.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            milMs.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            tfTempoDeClicker.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    min.setText(newValue.replaceAll("[^\\d]", ""));
                }
                armazenaDados();
            });
        }
    }

    /**
     * class para Parar/Pausar/Startar
     * Para o clicker de executar
     */
    public class ControleClicker implements NativeKeyListener {
        boolean ctrlOn = false;

        private boolean ctrlConfirma(boolean letra, boolean ctrlAtivo) {
            if (ctrl.isSelected()) {
                return letra && ctrlAtivo;
            } else {
                return letra;
            }
        }


        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        }

        @Override
        public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
            //(NativeInputEvent.CTRL_L_MASK & nativeKeyEvent.getKeyCode()) != 0
            String letra = (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
            System.out.println(letra);

            if (letra.equals("Ctrl") || letra.equals("Meta")) {
                ctrlOn = true;
            }

            if (ctrlConfirma(letra.equals(tfWait.getText()), ctrlOn) && !cbDesativarTeclado.isSelected()) {
                if (!pausa && pauseExecuter) {
                    pausa = true;
                    Platform.runLater(() -> iniciando.setText(pausaEm));
                } else if (pauseExecuter) {
                    pausa = false;
                    Platform.runLater(() -> iniciando.setText(andamentoEm));
                }
            }
            if (ctrlConfirma(letra.equals(tfStop.getText()), ctrlOn) && !cbDesativarTeclado.isSelected()) {
                setPodeExecutarClicke(false);
                pausa = false;
            }
            if (ctrlConfirma(letra.equals(tfStart.getText()), ctrlOn) && !cbDesativarTeclado.isSelected()) {
                setPodeExecutarClicke(true);
                clickerInfinite();
            }
        }

        @Override
        public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("Ctrl") || NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("Meta")) {
                ctrlOn = false;
            }
        }

    }

    /**
     * class para executar CallBack
     */
    public class CallBackEspera {

        /**
         * executa para dar um tempo ao usuário, entre outras utilizades.
         *
         * @param listener executa metodo click após acabar o programa de cronometro
         *                 (tempo)
         */
        void espera(CallBackInterface listener) {

            new Thread(() -> {
                iniciando.setVisible(true);
                progressbar.setVisible(true);
                if (cbTempoDeClicker.isSelected()) {
                    setPodeExecutarClicke(true);
                    for (int i = getTfTempoDeClicker(); 0 <= i; i--) {
                        try {
                            String tfStart = comecaEm +
                                    String.valueOf((double) i) +
                                    "s";
                            Platform.runLater(() -> iniciando.setText(tfStart));
                            if (!podeExecutarClicke) {
                                i -= getTfTempoDeClicker();
                                setPodeExecutarClicke(true);
                            }
                            Thread.sleep(1000);
                        } catch (InterruptedException | RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    listener.onFinish();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void ativarTeclado(boolean isTrue) throws NativeHookException {
        if (!isTrue) {
            GlobalScreen.registerNativeHook();
        } else {
            GlobalScreen.unregisterNativeHook();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new detecta().retiraNumerosClicker();
        lerDados();
        atualizaTeclasMostradas();
        verificaTempoDeClicker();
        manipulandoTabela();
        try {
            GlobalScreen.addNativeKeyListener(new ControleClicker());
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.WARNING);
            logger.setUseParentHandlers(false);
            ativarTeclado(cbDesativarTeclado.isSelected());
        } catch (NativeHookException e) {
            e.printStackTrace();
        }

        String tutorialClickerInfinite;
        String tutorialClickerTempo;
        switch (LANGUAGE) {
            case("pt") :
                comecaEm = "Começa em ";
                pausaEm = "Pausar";
                fimDaExecucao = "Fim da Execução";
                andamentoEm = "Em andamento...";
                tutorialClickerInfinite = "ClickerInfinite: serve para um clicker em\n" +
                        "velocidade já feita pelo usuário e um tempo \n" +
                        "infinito de clicker. Lembre-se, ele só Start e Stop.\n" +
                        "Sem Pause.";
                tutorialClickerTempo = "Você terá 3 espaçamentos h,m,s. dependendo do que colocar,\n" +
                        "ele dira o tempo de clicker que vai durar.\n" +
                        "Exemplo: Se você colocar pra 10s o seu clicker vai ficar clickando 10s\n" +
                        "com base na velocidade. Seu clicker vai durar 10s\n";
                break;
            case("fr") :
                comecaEm = "Commence à ";
                pausaEm = "Pause";
                fimDaExecucao = "Fin de l'exécution";
                andamentoEm ="En cours...";
                tutorialClickerInfinite = "ClickerInfinite: est pour un clicker dans\n" +
                        "la vitesse déjà faite par l'utilisateur et une\n" +
                        "clicker infini. Rappelez-vous que ce n'est que Start and Stop.\n" +
                        "Sans pause";
                tutorialClickerTempo = "Vous avez 3 espacements h, m, s. en fonction de ce qu'il faut faire,\n" +
                        "le temps de clic qui durera.\n" +
                        "Exemple: Si vous utilisez 10s, votre clic cliquera sur 10s\n" +
                        "basé sur la vitesse. Votre clicker durera 10s";
                break;
            default:
                comecaEm = "Starts at ";
                pausaEm = "Pause";
                fimDaExecucao = "End of Execution";
                andamentoEm = "In progress...";
                tutorialClickerInfinite = "ClickerInfinite: is for a clicker in\n" +
                        "speed already made by the user and a\n" +
                        "infinite clicker. Remember, it only Start and Stop.\n" +
                        "Without Pause.";
                tutorialClickerTempo = "You have 3 spacings h, m, s. depending on what to do,\n" +
                        "the clicker time that will last.\n" +
                        "Example: If you use 10s your click will be clicking on 10s\n" +
                        "based on speed. Your clicker will last 10s";
        }
        textoClicker.setText(tutorialClickerInfinite);
        textoTempo.setText(tutorialClickerTempo);
    }
}