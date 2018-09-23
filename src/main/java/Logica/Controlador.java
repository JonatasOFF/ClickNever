package Logica;

import Interfaces.CallBackInterface;
import Models.Mouse;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

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
 * @Version: 0.5
 *
 * @CoisasParaFazer: -MouseControler
 *
 *
 * @ATALHOS:
 * @*LD* : Leitura de dados (gravar,ler,resetar)
 * @*IR* : Mudanças de Janelas
 * @*INFORMACOES* : Dar informações ao usuário
 * @*CLICK* : Utilizações para os Clickers, Execução, Fazer.
 * @*VERIFICACOES* : Verifica se tal coisa deve ser executada ou não, utilizada ou não (Geralmente usada no Inicializable)
 * @*CONTROLER* : São relacionados ao Controler, porém sem nenhum Grupo em Conjunto (example ld tem leitura e gravação, portanto é um conjunto que um depende do outro)
 *
 *@CLASS:
 * @ControleMouse : Controla as partições do Mouse para modificações e pegar os inputs dele
 *
 * @ControleKeyboard : Controla o Keyboard, para Start,Pause ou Stop. Muito Util
 *
 * @Detecta : Ele serve para Detectar erros,letras,limite de numeros em lugares errados, Sempre utilizar pra quando quiser Limitar digitos do Usuario
 *
 * @CallBackEspera : Ele é o CallBackEspera, serve para dar um tempo entre a inicialização do Clicker, Por enqunato essa é sua unica utilização
 *
 */
public class Controlador implements Initializable {

    @FXML
    public TextField tfClickers;

    private int getTfTempoDeClicker() {
        if (tfTempoDeClicker.getText().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(tfTempoDeClicker.getText());
        }
    }

    private RadioButton getGroupBotoesVelocidade() {
        return (RadioButton) groupBotoes.getSelectedToggle();
    }

    void setPodeExecutarClicke(boolean podeExecutarClicke) {
        this.podeExecutarClicke = podeExecutarClicke;
    }

    private boolean clickerSelect;

    private boolean executouSet;


    private int tfSegundos = 0;

    private int tfMinutos = 0;

    private int horas = 0;

    private int vezes = 0;

    private int xNew;

    private int yNew;

    private boolean podeExecutarClicke = true;

    private boolean pausa;

    private boolean abertoTempo;

    private final static String LANGUAGE = System.getProperty("user.language");

    private boolean abertoClicker;

    private boolean jaExecutou = false;

    private boolean pauseExecuter = false;

    private String comecaEm;

    private String pausaEm;

    private String andamentoEm;

    private String fimDaExecucao;

    @FXML
    private Pane paneMouseControler;
    @FXML
    private Pane paneKeyboardMenu;
    @FXML
    private Pane paneClicker;
    @FXML
    public TableView<Mouse> tvTabela;
    @FXML
    public TableColumn<Mouse, Integer> x;
    @FXML
    public TableColumn<Mouse, Integer> y;
    @FXML
    public TableColumn<Mouse, Integer> clickers;
    @FXML
    public TableColumn<Mouse, Integer> ordem;
    @FXML
    public ComboBox<String> cboxOrdem;
    @FXML
    public CheckBox cbDesativarTeclado;
    @FXML
    public CheckBox cbCtrl;
    @FXML
    public CheckBox cbTempoDeClicker;
    @FXML
    public TextArea taClicker;
    @FXML
    public TextArea taTempo;
    @FXML
    public TextField tfStop;
    @FXML
    public TextField tfHours;
    @FXML
    public TextField tfMin;
    @FXML
    public TextField tfWait;
    @FXML
    public TextField tfTempoDeClicker;
    @FXML
    public TextField tfStart;
    @FXML
    public TextField tfSeg;
    @FXML
    private ProgressBar pbDuracao;
    @FXML
    private ToggleGroup groupBotoes;
    @FXML
    public ToggleGroup groupSelectionClick;
    @FXML
    public RadioButton rbClickerInfinite;
    @FXML
    public RadioButton rbClickTempo;
    @FXML
    private RadioButton rbOnems;
    @FXML
    private RadioButton rbDezms;
    @FXML
    private RadioButton rbCemms;
    @FXML
    private RadioButton rbMilms;
    @FXML
    public Label lbErrorComeca;
    @FXML
    public Label lbTempoDeClicker;
    @FXML
    public Label lbErrorPara;
    @FXML
    public Label lbErrorPausa;
    @FXML
    private Label lbIniciando;
    @FXML
    public Label lbTempo;
    @FXML
    public Label lbDuracao;
    @FXML
    public Label lbStart;
    @FXML
    public Label lbStop;
    @FXML
    public Label lbPause;
    @FXML
    public Label lbClicke;

    private ObservableList<Mouse> mousesUsados;

    private List<String> ordemM = new ArrayList<>();

    private ObservableList<String> ordemMouses;

    private List<Mouse> mouses = new ArrayList<>();

    //<IR>------------------------------------------------------------------------------------------------------------------------------

    @FXML
    private void irApaginaInicial() {
        paneMouseControler.setVisible(false);
        paneKeyboardMenu.setVisible(false);
        paneClicker.setVisible(true);
    }

    @FXML
    void irMouseControler() {
        paneClicker.setVisible(false);
        paneMouseControler.setVisible(true);
        paneKeyboardMenu.setVisible(false);
    }

    @FXML
    private void irKeyboard() {
        paneClicker.setVisible(false);
        paneMouseControler.setVisible(false);
        paneKeyboardMenu.setVisible(true);
    }

    //<\IR>------------------------------------------------------------------------------------------------------------------------------


    //<INFORMACOES>------------------------------------------------------------------------------------------------------------------------------

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
    public void informacoesTempo() {
        if (!abertoTempo) {
            abertoTempo = true;
            lbTempo.setVisible(true);
            taTempo.setVisible(true);
        } else {
            lbTempo.setVisible(false);
            taTempo.setVisible(false);
            abertoTempo = false;
        }
    }
    @FXML
    public void InformacoesClicke() {
        if (!abertoClicker) {
            abertoClicker = true;
            taClicker.setVisible(true);
            lbClicke.setVisible(true);
        } else {
            abertoClicker = false;
            taClicker.setVisible(false);
            lbClicke.setVisible(false);
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

    //<\INFORMACOES>------------------------------------------------------------------------------------------------------------------------------

    //<LEITURA DE DADOS>*LD*
    @FXML
    void limparDados() {
        tfHours.setText("");
        tfMin.setText("");
        tfSeg.setText("");
        tfTempoDeClicker.setText("5");
        tfStop.setText("Z");
        tfWait.setText("X");
        tfStart.setText("S");
        rbOnems.setSelected(false);
        rbDezms.setSelected(false);
        rbCemms.setSelected(false);
        rbMilms.setSelected(true);
        cbCtrl.setSelected(true);
        cbTempoDeClicker.setSelected(true);
        rbClickTempo.setSelected(false);
        rbClickerInfinite.setSelected(true);
        atualizaTeclasMostradas();
    }

    private void lerDados() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\" + "Saves" + "\\" + "save.ini")))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                System.out.println(Arrays.toString(dados));
                tfHours.setText(dados[0]);
                tfMin.setText(dados[1]);
                tfSeg.setText(dados[2]);
                tfTempoDeClicker.setText(dados[3]);
                tfStop.setText(dados[4]);
                tfWait.setText(dados[5]);
                tfStart.setText(dados[6]);
                rbOnems.setSelected(Boolean.parseBoolean(dados[7]));
                rbDezms.setSelected(Boolean.parseBoolean(dados[8]));
                rbCemms.setSelected(Boolean.parseBoolean(dados[9]));
                rbMilms.setSelected(Boolean.parseBoolean(dados[10]));
                cbCtrl.setSelected(Boolean.parseBoolean(dados[11]));
                cbTempoDeClicker.setSelected(Boolean.parseBoolean(dados[12]));
                rbClickerInfinite.setSelected(Boolean.parseBoolean(dados[13]));
                rbClickTempo.setSelected(Boolean.parseBoolean(dados[14]));
                }
            } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    private String getListaSave() {
        //hora,tfMinuto,tfSegundo,tempoComeca,tfStop,pausa/continua,tfStart,one,dez,cem,mil,cbCtrl,isTempoDeClicker,isClickerInfinite,isClickerTempo
        return tfHours.getText() +
                "," +
                tfMin.getText() +
                "," +
                tfSeg.getText() +
                "," +
                tfTempoDeClicker.getText() +
                "," +
                tfStop.getText() +
                "," +
                tfWait.getText() +
                "," +
                tfStart.getText() +
                "," +
                rbOnems.isSelected() +
                "," +
                rbDezms.isSelected() +
                "," +
                rbCemms.isSelected() +
                "," +
                rbMilms.isSelected() +
                "," +
                cbCtrl.isSelected() +
                "," +
                cbTempoDeClicker.isSelected() +
                "," +
                rbClickerInfinite.isSelected() +
                "," +
                rbClickTempo.isSelected();
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


    //<CLICK>------------------------------------------------------------------------------------------------------------------------------------

    @FXML
    public void stopClickerInfinite() {
        setPodeExecutarClicke(false);
    }
    @FXML
    public void clickerInfinite() {
        if (!jaExecutou) {
            if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                jaExecutou = true;
                cb.espera(() -> {
                    Platform.runLater(() -> lbIniciando.setText("Em andamento..."));
                    do {
                        Robot robotl = new Robot();
                        robotl.mousePress(InputEvent.BUTTON1_MASK);
                        robotl.mouseRelease(InputEvent.BUTTON1_MASK);
                        robotl.delay(getVelocidade(getGroupBotoesVelocidade().getText(),true));
                    } while (podeExecutarClicke);
                    jaExecutou = false;
                    setPodeExecutarClicke(true);
                    Platform.runLater(() -> lbIniciando.setText(fimDaExecucao));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> lbIniciando.setVisible(false));
                });
            } else {
                alertKeyboard();
            }
        }
    }
    @FXML
    private void executaMouseTempo() {
        try {
            if (!jaExecutou) {
                jaExecutou = true;
                if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                    pegaValores();
                    vezes = 0;
                    int tfMinTrue = tfMinutos * 60;
                    int tfHours = horas * 3600;
                    vezes = (tfSegundos + tfMinTrue + tfHours);
                    cb.espera(() -> {
                        try {
                            Platform.runLater(() -> lbIniciando.setText(andamentoEm));
                            executaClicker(vezes);
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

    private void pegaValores() {
        if (!(tfSeg.getText().equals(""))) {
            tfSegundos = Integer.parseInt(tfSeg.getText());
        }
        if (!(tfMin.getText().equals(""))) {
            tfMinutos = Integer.parseInt(tfMin.getText());
        }
        if (!(tfHours.getText().equals(""))) {
            if (horas > 24) {
                horas = 24;
            } else {
                horas = Integer.parseInt(tfHours.getText());
            }
        }
    }

    private void tempo(int tfSegundos) {
        int h = tfSegundos / 3600;
        int m = (tfSegundos % 3600) / 60;
        int s = (tfSegundos % 3600) % 60;
        Platform.runLater(() -> lbDuracao.setText(h + " : " + m + " : " + s));
    }

    private void pauseClicker() throws InterruptedException {
        if (pausa) {
            Thread.sleep(3000);
            if (!podeExecutarClicke) {
                pausa = false;
                Platform.runLater(() -> lbIniciando.setText(andamentoEm));
            } else {
                pauseClicker();
            }
        }
    }

    private int getVelocidade(String velocidade,boolean isVelocidade) {
        //isVelocidade true = Velocidade de Clicker(ser executada em Clicker), false = Pegar valor da velocidade(Ser executada em Calculo de Tempo ou Quantidade de Clicker)
        if (isVelocidade) {
            switch (velocidade) {
                case ("0.001/s"):
                    return 1;
                case ("0.01/s"):
                    return 10;
                case ("0.1/s"):
                    return 100;
                case ("1/s"):
                    return 1000;
                default:
                return 1000;
            }
        } else {
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
    }

    /**
     * @param quantas Pega valor de Quantos Clicker a serem executados
     * @throws AWTException         null
     * @throws InterruptedException null
     */
    private void executaClicker(double quantas) throws AWTException, InterruptedException {
        if (quantas != 0) {
            pauseExecuter = true;
            setPodeExecutarClicke(true);
            Robot robot = new Robot();
            quantas *= getVelocidade(getGroupBotoesVelocidade().getText(),false);
            for (double i = 0; i <= quantas && podeExecutarClicke; i++) {
                tempo((int) (quantas - i) / getVelocidade(getGroupBotoesVelocidade().getText(),false));

                pbDuracao.setProgress(i / quantas);

                robot.delay(getVelocidade(getGroupBotoesVelocidade().getText(),true));
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
            lbIniciando.setText(fimDaExecucao);
            lbDuracao.setText("0 : 0 : 0");
            pbDuracao.setProgress(0);
            pbDuracao.indeterminateProperty();
        });
        Thread.sleep(2000);
        Platform.runLater(() -> {
            lbIniciando.setVisible(false);
            pauseExecuter = false;
        });
    }

    //<\CLICK>-------------------------------------------------------------------------------------------------------------------------------------

    //<VERIFICACOES>-------------------------------------------------------------------------------------------------------------------------------
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

    private void ativarTeclado(boolean isTrue) throws NativeHookException {
        if (!isTrue) {
            GlobalScreen.registerNativeHook();
        } else {
            GlobalScreen.unregisterNativeHook();
        }
    }

    private void atualizaTeclasMostradas() {
        if (cbCtrl.isSelected()) {
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

    private void manipulandoTabela() {

        tvTabela.getItems().clear();

        System.out.println(mouses.toString());
        mousesUsados = FXCollections.observableArrayList(mouses);
        tvTabela.setItems(mousesUsados);
    }

    private void manipulaCboxText() {
        if (!mouses.isEmpty()) {
            ordemM.clear();
            mouses.forEach(k -> ordemM.add(String.valueOf(k.getOrdem())));
            ordemMouses = FXCollections.observableArrayList(ordemM);
            cboxOrdem.setItems(ordemMouses);
        }
    }

    //<\VERIFICACOES>------------------------------------------------------------------------------------------------------------------------------

    //<CONTROLER>------------------------------------------------------------------------------------------------------------------------------

    @FXML
    public void setClickers() {
        if(!executouSet) {
            executouSet = true;
        }
    }

    //<\CONTROLER>------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Detecta().retiraNumerosClicker();
        lerDados();
        atualizaTeclasMostradas();
        verificaTempoDeClicker();
        manipulandoTabela();
        try {
            GlobalScreen.addNativeKeyListener(new ControleKeyBoard());
            GlobalScreen.addNativeMouseListener(new ControleMouse());
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.WARNING);
            logger.setUseParentHandlers(false);
            ativarTeclado(cbDesativarTeclado.isSelected());
            x.setCellValueFactory(new PropertyValueFactory<>("x"));
            y.setCellValueFactory(new PropertyValueFactory<>("y"));
            clickers.setCellValueFactory(new PropertyValueFactory<>("clickers"));
            ordem.setCellValueFactory(new PropertyValueFactory<>("ordem"));
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
        taClicker.setText(tutorialClickerInfinite);
        taTempo.setText(tutorialClickerTempo);
    }

    public class Detecta {

        void retiraNumerosClicker() {
            tfHours.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tfHours.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.matches("\\d\\d\\d")) {
                    tfHours.setText(tfHours.getText(0, 2) + "");
                }
                armazenaDados();
            });
            tfMin.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tfMin.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.matches("\\d\\d\\d")) {
                    tfMin.setText(tfMin.getText(0, 2) + "");
                }
                armazenaDados();
            });
            tfSeg.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tfSeg.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.matches("\\d\\d\\d")) {
                    tfSeg.setText(tfSeg.getText(0, 2) + "");
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
            cbCtrl.selectedProperty().addListener((observable, oldValue, newValue) -> {
                atualizaTeclasMostradas();
                armazenaDados();
            });
            rbOnems.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            rbDezms.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            rbCemms.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            rbMilms.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            tfTempoDeClicker.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tfMin.setText(newValue.replaceAll("[^\\d]", ""));
                }
                armazenaDados();
            });
            rbClickTempo.selectedProperty().addListener((observable, oldValue, newValue) -> {
                clickerSelect = true;
                armazenaDados();
            });
            rbClickerInfinite.selectedProperty().addListener((observable, oldValue, newValue) -> {
                clickerSelect = false;
                armazenaDados();
            });
            cboxOrdem.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    Platform.runLater(() -> {
                        System.out.println(newValue);
                        if(mousesUsados.get(Integer.parseInt(newValue) - 1).getClickers() != 0) {
                            tfClickers.setText(String.valueOf(mousesUsados.get(Integer.parseInt(newValue) - 1).getClickers()));
                        } else {
                            tfClickers.setText("");
                        }
                    });
                }
            });
            tfClickers.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    mouses.get(Integer.parseInt(cboxOrdem.getEditor().getText()) - 1).setClickers(Integer.parseInt(newValue));
                    manipulandoTabela();
                }catch (Exception ignore) {}
            });
        }
    }

    public class ControleMouse implements NativeMouseInputListener {

        @Override
        public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        }

        @Override
        public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        }

        @Override
        public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
                if (executouSet) {
                    mouses.add(new Mouse(nativeMouseEvent.getX(), nativeMouseEvent.getY(), 0, mouses.size() + 1));
                    manipulandoTabela();
                    manipulaCboxText();
                    executouSet = false;
                }

                System.out.printf("X : %d Y : %d \n", nativeMouseEvent.getX(), nativeMouseEvent.getY());
        }

        @Override
        public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {

        }

        @Override
        public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

        }
    }

    public class ControleKeyBoard implements NativeKeyListener {

        boolean cbCtrlOn = false;

        private boolean cbCtrlConfirma(boolean letra, boolean cbCtrlAtivo) {
            if (cbCtrl.isSelected()) {
                return letra && cbCtrlAtivo;
            } else {
                return letra;
            }
        }



        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        }

        @Override
        public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
            String letra = (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode())).toUpperCase();
            System.out.println(letra);

            if (letra.equals("CTRL") || letra.equals("META") || letra.equals("COMMAND") || letra.equals("CMD")) {
                cbCtrlOn = true;
            }

            if (cbCtrlConfirma(letra.equals(tfWait.getText()), cbCtrlOn) && !cbDesativarTeclado.isSelected()) {
                if (!pausa && pauseExecuter) {
                    pausa = true;
                    Platform.runLater(() -> lbIniciando.setText(pausaEm));
                } else if (pauseExecuter) {
                    pausa = false;
                    Platform.runLater(() -> lbIniciando.setText(andamentoEm));
                }
            }
            if (cbCtrlConfirma(letra.equals(tfStop.getText()), cbCtrlOn) && !cbDesativarTeclado.isSelected()) {
                setPodeExecutarClicke(false);
                pausa = false;
            }
            if (cbCtrlConfirma(letra.equals(tfStart.getText()), cbCtrlOn) && !cbDesativarTeclado.isSelected()) {
                setPodeExecutarClicke(true);
                if(clickerSelect) {
                    executaMouseTempo();
                } else {
                    clickerInfinite();
                }
            }
        }

        @Override
        public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            String letra = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).toUpperCase();
            if (letra.equals("CTRL") || letra.equals("META") || letra.equals("COMMAND") || letra.equals("CMD")) {
                cbCtrlOn = false;
            }
        }
    }

    public class CallBackEspera {

        /**
         * executa para dar um tempo ao usuário, entre outras utilizades.
         *
         * @param listener executa metodo click após acabar o programa de cronometro
         *                 (tempo)
         */
        void espera(CallBackInterface listener) {

            new Thread(() -> {
                lbIniciando.setVisible(true);
                pbDuracao.setVisible(true);
                if (cbTempoDeClicker.isSelected()) {
                    setPodeExecutarClicke(true);
                    for (int i = getTfTempoDeClicker(); 0 <= i; i--) {
                        try {
                            String tfStart = comecaEm +
                                    String.valueOf((double) i) +
                                    "s";
                            Platform.runLater(() -> lbIniciando.setText(tfStart));
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

    private CallBackEspera cb = new CallBackEspera();
}