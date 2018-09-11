package Logica;

import Calls.CallBackInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @Author: Jonatas De Oliveira Ferreira
 * @Version: 0.4
 *
 * Coisas a se fazer Version 0.4:
 *
 * Mudar a lingua para PT e EUA
 * Entrar em contato com Design para mudar interface
 * Verificar se é possivel dar uma introdução a *ClickerControler*
 * Fazer documentação.
 *
 */
public class Controlador implements Initializable {

    private CallBackEspera cb = new CallBackEspera();

    private int segundos = 0;

    private int minutos = 0;

    private int horas = 0;

    private int vezes = 0;

    private int tempoDeClicker;

    private boolean podeExecutarClicke = true;

    private boolean pausa;

    private boolean abertoTempo;

    private boolean abertoClicker;

    private boolean jaExecutou = false;

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

    void setPodeExecutarClicke(boolean podeExecutarClicke) {
        this.podeExecutarClicke = podeExecutarClicke;
    }

    private void setProgressbar(double value) {
        progressbar.setProgress(value);
    }

    @FXML
    public Label lbDuracao;
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
    public TextField teclaStop;
    @FXML
    public Button btAtivaMouse;
    @FXML
    public Button btAtivaMouseTempo;
    @FXML
    public TextArea textoTempo;
    @FXML
    public Label lbTempo;
    @FXML
    public TextField pausar;
    @FXML
    public TextField comeca;
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
    private TextField tfnumeros;
    @FXML
    private Pane panelClicker;
    @FXML
    private Pane panelSobre;
    @FXML
    public TextArea taTextoSobre;
    @FXML
    public TextArea textoClicker;
    @FXML
    public Label lbClicke;

    @FXML
    private void irApaginaInicial() {
        panelSobre.setVisible(false);
        MouseControler.setVisible(false);
        KeyboardMenu.setVisible(false);
        panelClicker.setVisible(true);
    }

    @FXML
    private void irAtualizasoes() {
        panelClicker.setVisible(false);
        MouseControler.setVisible(false);
        KeyboardMenu.setVisible(false);
        panelSobre.setVisible(true);

    }

    @FXML
    private void irKeyboard() {
        panelClicker.setVisible(false);
        MouseControler.setVisible(false);
        panelSobre.setVisible(false);
        KeyboardMenu.setVisible(true);
    }

    /**
     * Projeto em Alpha
     * Serve para automatizar qualquer Game Clicker
     */
    @FXML
    void irMouseControler() {
//        panelClicker.setVisible(false);
//        panelSobre.setVisible(false);
//        KeyboardMenu.setVisible(false);
//        //Toolkit tk = Toolkit.getDefaultToolkit();
//        //Dimension d = tk.getScreenSize();
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Em breve...");
//        //alert.setHeight(d.height - d.height/2);
//        //alert.setWidth(d.width - d.width/2);
//        alert.setHeaderText("Esse sistema por enquanto está em fase Alpha");
//        alert.setContentText("Não nos responsabilizamos de erros,bugs ou seja lá o que você encontrar aqui");
//        alert.show();
//        //Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        MouseControler.setVisible(true);
    }

    @FXML
    private void avisoVelocidade() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Aviso");
        alert.setContentText("Essa opção pode trazer Perda de clickes");
        alert.show();
    }

    /**
     * Modo de execução do mouse
     * serve para clickar automaticamente
     */
    @FXML
    void executaMouse() {
        if (!jaExecutou) {
            jaExecutou = true;
            if (!(pausar.getText().equals(teclaStop.getText()) || teclaStop.getText().equals(comeca.getText()) || comeca.getText().equals(pausar.getText()))) {
                btAtivaMouseTempo.setCancelButton(false);
                btAtivaMouse.setCancelButton(false);
                cb.espera(() -> {
                    try {
                        pegaValores();
                        //interface que faz aparecer
                        Platform.runLater(() -> iniciando.setText("Em andamento..."));
                            executaclicker(vezes);
                            jaExecutou = false;
                    } catch (InterruptedException | AWTException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
                alert.setTitle("Error");
                alert.setHeaderText("Existem letras iguais ou faltam letras");
                alert.setContentText("Por favor acessar Opções -> Keyboard, e Resolver os erros");
                jaExecutou = false;
            }
        }
    }

    @FXML
    public void informacaoReseta() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.show();
        alert.setTitle("Reset");
        alert.setContentText("Lembre-se. Todos os dados salvos serão resetados");
        alert.setHeaderText("Aqui você reseta todas as suas informações");
    }

    @FXML
    void limparDados() {
        hours.setText("");
        min.setText("");
        seg.setText("");
        tfnumeros.setText("");
        tfTempoDeClicker.setText("5");
        teclaStop.setText("Z");
        pausar.setText("X");
        comeca.setText("S");
        oneMs.setSelected(false);
        dezms.setSelected(false);
        cemMs.setSelected(false);
        milMs.setSelected(true);
        ctrl.setSelected(true);
        cbTempoDeClicker.setSelected(true);
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
    private void executaMouseTempo() {
        try {
            if (!jaExecutou) {
                jaExecutou = true;
                if (!(pausar.getText().equals(teclaStop.getText()) || teclaStop.getText().equals(comeca.getText()) || comeca.getText().equals(pausar.getText()))) {
                    pegaValores();
                    vezes = 0;
                    int minTrue = minutos * 60;
                    int hours = horas * 3600;
                    vezes = (segundos + minTrue + hours);
                    cb.espera(() -> {
                        try {
                            Platform.runLater(() -> iniciando.setText("Em andamento..."));
                            executaclicker(vezes);
                            jaExecutou = false;

                        } catch (AWTException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.show();
                    alert.setTitle("Error");
                    alert.setHeaderText("Existem letras iguais ou faltam letras");
                    alert.setContentText("Por favor acessar Opções -> Keyboard, e Resolver os erros");
                    jaExecutou = false;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            jaExecutou = false;
        }
    }

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

    private int getNumeros() {
        if (!tfnumeros.getText().equals("")) {
            return Integer.parseInt(tfnumeros.getText());
        } else {
            return 0;
        }
    }

    private String getListaSave() {
        //hora,minuto,segundo,numeroClicker,tempoComeca,teclaStop,pausa/continua,comeca,one,dez,cem,mil,ctrl,isTempoDeClicker
        return hours.getText() +
                "," +
                min.getText() +
                "," +
                seg.getText() +
                "," +
                tfnumeros.getText() +
                "," +
                tfTempoDeClicker.getText() +
                "," +
                teclaStop.getText() +
                "," +
                pausar.getText() +
                "," +
                comeca.getText() +
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

    private void lerDados() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\" + "Saves" + "\\" + "save.ini")))) {
            String linha;
            while((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                hours.setText(dados[0]);
                min.setText(dados[1]);
                seg.setText(dados[2]);
                tfnumeros.setText(dados[3]);
                tfTempoDeClicker.setText(dados[4]);
                teclaStop.setText(dados[5]);
                pausar.setText(dados[6]);
                comeca.setText(dados[7]);
                oneMs.setSelected(Boolean.parseBoolean(dados[8]));
                dezms.setSelected(Boolean.parseBoolean(dados[9]));
                cemMs.setSelected(Boolean.parseBoolean(dados[10]));
                milMs.setSelected(Boolean.parseBoolean(dados[11]));
                ctrl.setSelected(Boolean.parseBoolean(dados[12]));
                cbTempoDeClicker.setSelected(Boolean.parseBoolean(dados[13]));
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
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
            alert.setTitle("ERRO DO ARMAZENAMENTO");
            alert.setContentText(System.getProperty("user.dir"));
            e.printStackTrace();
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
        vezes = getNumeros();
    }

    private void pauseClicker() throws InterruptedException {
        if (pausa) {
            Thread.sleep(3000);
            if (!podeExecutarClicke) {
                pausa = false;
            } else {
                pauseClicker();
            }
        }
    }

    private void tempo(int tempo) {
        int hourasTempo = tempo / 3600;
        int minTempo = (tempo % 3600) / 60;
        int secTempo = (tempo % 3600) % 60;
        Platform.runLater(() -> lbDuracao.setText(hourasTempo + " : " + minTempo + " : " + secTempo));
    }

    /**
     * @param quantas Pega valor de Quantos Clicker a serem executados
     * @throws AWTException null
     * @throws InterruptedException null
     */
    private void executaclicker(double quantas) throws AWTException, InterruptedException {
        if(quantas != 0) {
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
                alert.setTitle("Error");
                alert.setContentText("Não a números a serem executados");
            });
        }
        Platform.runLater(() -> {
            iniciando.setText("Fim da execução");
            lbDuracao.setText("0 : 0 : 0");
            progressbar.setProgress(0);
            progressbar.indeterminateProperty();
        });
        Thread.sleep(1000);
        Platform.runLater(() -> {
            progressbar.setVisible(false);
            iniciando.setVisible(false);
            btAtivaMouse.setCancelButton(false);
            btAtivaMouseTempo.setCancelButton(false);
        });
    }

    /**
     * Detectar/Funcionar/Programa Aberto
     * A classe Detecta serve para ver se existe algum valor errado
     * Também funciona como a classe que lança coisas a se fazer enquanto
     * o programa está aberto
     */
    public class detecta extends Thread implements Runnable {

        @Override
        public void run() {
            retiraNumerosClicker();
        }

        void retiraNumerosClicker() {
            tfnumeros.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tfnumeros.setText(newValue.replaceAll("[^\\d]", ""));
                }
                armazenaDados();
            });
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
            teclaStop.textProperty().addListener((observable, oldValue, newValue) -> {
                teclaStop.setText(teclaStop.getText().toUpperCase());
                try {
                    if (!newValue.matches("\\w")) {
                        teclaStop.setText(teclaStop.getText(1, 2));
                    }
                } catch (Exception ignore) {
                }
                if (teclaStop.getText().equals(comeca.getText())) {
                    lbErrorPara.setVisible(true);
                }
                if (teclaStop.getText().equals(pausar.getText())) {
                    lbErrorPara.setVisible(true);
                }
                if (!(teclaStop.getText().equals(pausar.getText()) || teclaStop.getText().equals(comeca.getText()))) {
                    lbErrorPara.setVisible(false);
                }
                if (!(teclaStop.getText().equals(pausar.getText()) || pausar.getText().equals(comeca.getText()))) {
                    lbErrorPausa.setVisible(false);
                } else {
                    lbErrorPausa.setVisible(true);
                }
                if (!(teclaStop.getText().equals(comeca.getText()) || comeca.getText().equals(pausar.getText()))) {
                    lbErrorComeca.setVisible(false);
                } else {
                    lbErrorComeca.setVisible(true);
                }
                if (teclaStop.getText().equals("")) {
                    lbErrorPausa.setVisible(true);
                }
                armazenaDados();
            });
            comeca.textProperty().addListener((observable, oldValue, newValue) -> {
                comeca.setText(comeca.getText().toUpperCase());
                try {
                    if (!newValue.matches("\\w")) {
                        comeca.setText(comeca.getText(1, 2));
                    }
                } catch (Exception ignore) {
                }
                if (comeca.getText().equals(teclaStop.getText())) {
                    lbErrorComeca.setVisible(true);
                }
                if (comeca.getText().equals(pausar.getText())) {
                    lbErrorComeca.setVisible(true);
                }
                if (!(comeca.getText().equals(pausar.getText()) || comeca.getText().equals(teclaStop.getText()))) {
                    lbErrorComeca.setVisible(false);
                }
                if (!(comeca.getText().equals(pausar.getText()) || pausar.getText().equals(teclaStop.getText()))) {
                    lbErrorPausa.setVisible(false);
                } else {
                    lbErrorPausa.setVisible(true);
                }
                if (!(comeca.getText().equals(teclaStop.getText()) || teclaStop.getText().equals(pausar.getText()))) {
                    lbErrorPara.setVisible(false);
                } else {
                    lbErrorPara.setVisible(true);
                }
                if (comeca.getText().equals("")) {
                    lbErrorPausa.setVisible(true);
                }
                armazenaDados();
            });
            pausar.textProperty().addListener((observable, oldValue, newValue) -> {
                pausar.setText(pausar.getText().toUpperCase());
                try {
                    if (!newValue.matches("\\w")) {
                        pausar.setText(pausar.getText(1, 2));
                    }
                } catch (Exception ignore) {
                }
                if (pausar.getText().equals(teclaStop.getText())) {
                    lbErrorPausa.setVisible(true);
                }
                if (pausar.getText().equals(comeca.getText())) {
                    lbErrorPausa.setVisible(true);
                }
                if (!(pausar.getText().equals(comeca.getText()) || pausar.getText().equals(teclaStop.getText()))) {
                    lbErrorPausa.setVisible(false);
                }
                if (!(pausar.getText().equals(comeca.getText()) || comeca.getText().equals(teclaStop.getText()))) {
                    lbErrorComeca.setVisible(false);
                } else {
                    lbErrorComeca.setVisible(true);
                }
                if (!(pausar.getText().equals(teclaStop.getText()) || teclaStop.getText().equals(pausar.getText()))) {
                    lbErrorPara.setVisible(false);
                } else {
                    lbErrorPara.setVisible(true);
                }
                if (pausar.getText().equals("")) {
                    lbErrorPausa.setVisible(true);
                }
                armazenaDados();
            });
            cbTempoDeClicker.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if(cbTempoDeClicker.isSelected()) {
                    tfTempoDeClicker.setOpacity(1);
                    tfTempoDeClicker.setEditable(true);
                    lbTempoDeClicker.setOpacity(1);
                } else {
                    tfTempoDeClicker.setOpacity(0.50);
                    tfTempoDeClicker.setEditable(false);
                    lbTempoDeClicker.setOpacity(0.50);
                }
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

            if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("Ctrl")) {
                ctrlOn = true;
            }

            if (ctrlConfirma(letra.equals(pausar.getText()), ctrlOn)) {
                if (!pausa) {
                    pausa = true;
                    Platform.runLater(() -> iniciando.setText("Pausado"));
                } else {
                    pausa = false;
                    Platform.runLater(() -> iniciando.setText("Em andamento..."));
                }
            }
            if (ctrlConfirma(letra.equals(teclaStop.getText()), ctrlOn)) {
                setPodeExecutarClicke(false);
            }
            if (ctrlConfirma(letra.equals(comeca.getText()), ctrlOn)) {
                executaMouseTempo();
            }
        }

        @Override
        public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            if ((NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("Ctrl"))) {
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
                if(cbTempoDeClicker.isSelected()) {
                    setPodeExecutarClicke(true);
                    for (int i = getTfTempoDeClicker(); 0 <= i; i--) {
                        StringBuilder sb = new StringBuilder();
                        try {
                            sb.append("Começa em ");
                            sb.append(String.valueOf((double) i));
                            sb.append("s");
                            Platform.runLater(() -> iniciando.setText(sb.toString()));
                            if(!podeExecutarClicke) {
                                i -= getTfTempoDeClicker();
                                setPodeExecutarClicke(true);
                            }
                            Thread.sleep(1000);
                        } catch (InterruptedException | RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }
                listener.onFinish();
            }).start();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(location);
        System.out.println(location.getPath());
        System.out.println(resources.getKeys());
        System.out.println(resources.getLocale());

        new detecta().start();
        lerDados();
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new ControleClicker());
        } catch (NativeHookException e) {
            e.printStackTrace();
        }

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        logger.setUseParentHandlers(false);

        if(cbTempoDeClicker.isSelected()) {
            tfTempoDeClicker.setOpacity(1);
            tfTempoDeClicker.setEditable(true);
            lbTempoDeClicker.setOpacity(1);
        } else {
            tfTempoDeClicker.setOpacity(0.50);
            tfTempoDeClicker.setEditable(false);
            lbTempoDeClicker.setOpacity(0.50);
        }
    }
}