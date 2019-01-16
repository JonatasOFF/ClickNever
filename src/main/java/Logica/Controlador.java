package Logica;

import Interfaces.CallBackInterface;
import Models.Mouse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @Author: Jonatas De Oliveira Ferreira
 * @Version: 0.7
 *
 *
 * @Mudancas : Resolver bug de tempo, colocar lopp em MouseControler, por enquanto isso -> 0.8
 * git push origin master or git push
 *
 *
 * @ATALHOS:
 * @*LD* : Leitura de dados (gravar,ler,resetar)
 * @*IR* : Mudancas de Janelas
 * @*INFORMACOES* : Dar informacões ao usuário
 * @*CLICK* : Utilizacões para os Clickers, Execucão, Fazer.
 * @*VERIFICACOES* : Verifica se tal coisa deve ser executada ou não, utilizada ou não (Geralmente usada no Inicializable)
 * @*CONTROLER* : São relaciondos ao Controler, porém sem nenhum Grupo em Conjunto (example *LD* tem leitura e gravacão, portanto é um conjunto que um depende do outro)
 *
 * Se estiver utilizando a Intellj para mudancas no codigo, por favor, use CTRL + SHIFT + -
 * Para minimizar os metodos ou class
 *
 *@CLASS:
 * @ControleMouse : Controla as particões do Mouse para modificacões e pegar os inputs dele
 *
 * @ControleKeyboard : Controla o Keyboard, para Start,Pause ou Stop. Muito Util
 *
 * @Detecta : Ele serve para Detectar erros,letras,limite de numeros em lugares errados, Sempre utilizar pra quando quiser Limitar digitos do Usuario
 *
 * @CallBackEspera : Ele é o CallBackEspera, serve para dar um tempo entre a inicializacão do Clicker, Por enqunato essa é sua unica utilizacão
 *
 *
 */
public class Controlador implements Initializable {

    @FXML
    public TextField tfClickers;
    @FXML
    public ToggleGroup groupBotoes1;
    @FXML
    public ComboBox<String> cboxMudancaIndex;
    @FXML
    public Label lbIndexPara;
    @FXML
    public RadioButton rbMouseControler;
    @FXML
    public Label lbIniciadoControler;
    @FXML
    public Label lbMostraTeclaClicker;
    @FXML
    public Label lbDeleteIndex;

    private int positionX;

    private int positionY;

    private RadioButton getGroupBotoesVelocidade1() {
        return (RadioButton) groupBotoes1.getSelectedToggle();
    }

    private RadioButton getGroupBotoesVelocidade() {
        return (RadioButton) groupBotoes.getSelectedToggle();
    }

    @FXML
    public AnchorPane painelPrincipal;

    private final static String LANGUAGE = System.getProperty("user.language");

    private boolean isSetControler;

    private boolean isExecutableMouseControler = true;

    private boolean isTutorialMouseControler;

    private boolean isControlerMouse = true;

    private boolean isPause;

    private boolean isTutorialTempo;

    private boolean isTutorialClickerInfinite;

    private boolean isSetClickers = true;

    private int segundos = 0;

    private int minutos = 0;

    private int horas = 0;

    private int vezes = 0;

    //Esse cara diz se PODE EXECUTAR CLICKER;

    private boolean isExecutableClicker = true;
    //Esse cara diz se PODE EXECUTAR UMA AÇÂO != Clicker;

    private boolean isExecutable = true;
    //Seta para se tal acao deve ter um pause ou nao;

    private boolean isYesPause;

    private String comecaEm;

    private String pausaEm;

    private String andamentoEm;

    private String fimDaExecucao;

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

    public void setExecutableClicker(boolean isExecutableClicker) {
        this.isExecutableClicker = isExecutableClicker;
    }
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
    public Label lbDuracao;
    @FXML
    public Label lbStart;
    @FXML
    public Label lbStop;
    @FXML
    public Label lbPause;

    private List<Mouse> mousesList = new ArrayList<>();

    private ObservableList<Mouse> mousesObservableList;

    private List<String> indexMouse = new ArrayList<>();

    //<INFORMACOES>------------------------------------------------------------------------------------------------------------------------------
    private void alertKeyboard() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.show();
        System.out.println(LANGUAGE);
        switch (LANGUAGE) {
            case ("pt"):
                alert.setTitle("Error");
                alert.setContentText("Por favor acesse Opcões -> Teclado, e Resolva Erros");
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
        isExecutable = true;
    }

    @FXML
    private void avisoVelocidade() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.show();
        switch (LANGUAGE) {
            case ("pt"):
                alert.setTitle("Aviso");
                alert.setContentText("Esta opcão pode levar a uma perda de Clicker");
                alert.setHeaderText("Aviso");
                break;
            case ("fr"):
                alert.setTitle("Avis");
                alert.setContentText("Cette option peut entraîner une perte de clics");
                alert.setHeaderText("Avis");
                break;
            default:
                alert.setTitle("Notice");
                alert.setContentText("This opcão can lead to a clickthrough");
                alert.setHeaderText("Notice");
        }
    }
    @FXML
    public void informacoesTempo() {
        if (!isTutorialTempo) {
            isTutorialTempo = true;
            taTempo.setVisible(true);
        } else {
            taTempo.setVisible(false);
            isTutorialTempo = false;
        }
    }
    @FXML
    public void InformacoesClicke() {
        if (!isTutorialClickerInfinite) {
            isTutorialClickerInfinite = true;
            taClicker.setVisible(true);
        } else {
            isTutorialClickerInfinite = false;
            taClicker.setVisible(false);
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
                alert.setContentText("Comandos do teclado não respondem a essa opcão desativada");
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
                alert.setHeaderText("Aqui você reseta todas as suas informacões");
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
    /*
    @FXML
    public void informacoesControler() {
        isTutorialMouseControler = !isTutorialMouseControler;
    }
    */

    //<\INFORMACOES>------------------------------------------------------------------------------------------------------------------------------]

    //<LD>------------------------------------------------------------------------------------------------------------------------------------

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
        rbMouseControler.setSelected(false);
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
                rbMouseControler.setSelected(Boolean.parseBoolean(dados[15]));
                }
            } catch (IOException ignored) {}
    }

    private String getListaSave() {
        //hora,tfMinuto,tfSegundo,tempoComeca,tfStop,isPause/continua,tfStart,one,dez,cem,mil,cbCtrl,isTempoDeClicker,isClickerInfinite,isClickerTempo
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
                rbMouseControler.isSelected() +
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

    //<\LD>------------------------------------------------------------------------------------------------------------------------------------

    //<CLICK>------------------------------------------------------------------------------------------------------------------------------------

    @FXML
    public void clickerInfinite() {
        if (isExecutable) {
            if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                isExecutable = false;
                cb.espera(() -> {
                    Platform.runLater(() -> {
                        lbIniciando.setText("Em andamento...");
                        pbDuracao.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                    });
                    do {
                        Robot robotl;
                        try {
                            robotl = new Robot();
                            robotl.mousePress(InputEvent.BUTTON1_MASK);
                            robotl.mouseRelease(InputEvent.BUTTON1_MASK);
                            robotl.delay(getVelocidade(getGroupBotoesVelocidade().getText(),true));
                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
                    } while (isExecutableClicker);
                    isExecutable = true;
                    setExecutableClicker(true);
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
    public void setClickers() {
        if(!isSetControler) {
            isSetControler = true;
        }
    }
    @FXML
    private void executaMouseTempo() {
        try {
            if (isExecutable) {
                isExecutable = false;
                if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                    pegaValores();
                    vezes = 0;
                    int minuto = minutos * 60;
                    int hora = horas * 3600;
                    vezes = (segundos + minuto + hora);
                    cb.espera(() -> {
                        try {
                            Platform.runLater(() -> lbIniciando.setText(andamentoEm));
                            executaClicker(vezes);
                            isExecutable = true;
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
            isExecutable = true;
        }
    }

    private void pegaValores() {
        if (!(tfSeg.getText().equals(""))) {
            segundos = Integer.parseInt(tfSeg.getText());
        }
        if (!(tfMin.getText().equals(""))) {
            minutos = Integer.parseInt(tfMin.getText());
        }
        if (!(tfHours.getText().equals(""))) {
            if (horas > 24) {
                horas = 24;
            } else {
                horas = Integer.parseInt(tfHours.getText());
            }
        }
    }

    private void tempo(int segundos) {
        int h = segundos / 3600;
        int m = (segundos % 3600) / 60;
        int s = (segundos % 3600) % 60;
        Platform.runLater(() -> lbDuracao.setText(h + " : " + m + " : " + s));
    }

    //aqui é utilizado recursividade, se o usuario clickou então ele fica em loop até o usuario voltar a clickar no pause.
    private void pauseClicker()  {
        if (isPause) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!isExecutableClicker) {
                isPause = false;
                Platform.runLater(() -> lbIniciando.setText(andamentoEm));
                Platform.runLater(() -> lbIniciadoControler.setText(andamentoEm));

            } else {
                pauseClicker();
            }
        }
    }

    private int getVelocidade(String velocidade, boolean isVelocidade) {
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

    @FXML
    public void stopClickerInfinite() {
        setExecutableClicker(false);
        pbDuracao.setProgress(0);
    }

    /**
     * @param quantas Pega valor de Quantos Clicker a serem executados
     * @throws AWTException         null
     * @throws InterruptedException null
     */
    private void executaClicker(double quantas) throws AWTException,InterruptedException {
        if (quantas != 0) {
            isYesPause = true;
            setExecutableClicker(true);
            Robot robot = new Robot();
            quantas *= getVelocidade(getGroupBotoesVelocidade().getText(),false);
            for (double i = 0; i <= quantas && isExecutableClicker; i++) {
                //tempo para o usuario ver quando vai terminar
                tempo((int) (quantas - i) / getVelocidade(getGroupBotoesVelocidade().getText(),false));

                //duracao no progressbar
                pbDuracao.setProgress(i / quantas);

                //execucao do clicker
                robot.delay(getVelocidade(getGroupBotoesVelocidade().getText(),true));
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                //metodo chamado caso o usuario clickou para pausar, se não volta ao normal
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
            isYesPause = false;
        });
    }

    @FXML
    public void executaMouseControler() {
        //nao executar 2 vezes
        if(isControlerMouse && isExecutableClicker && !mousesList.isEmpty()) {
            isExecutableMouseControler = true;
            isExecutableClicker = false;
            cb.esperaControler(() -> {
                clickerActive();
                Platform.runLater(() -> lbIniciadoControler.setText(fimDaExecucao));
                Thread.sleep(2000);
                Platform.runLater(() -> lbIniciadoControler.setVisible(false));
                isControlerMouse = true;
                isExecutableMouseControler = true;
                isExecutableClicker = true;
                isYesPause = false;
            });
        }
    }


    private void clickerActive() {
        try {
            Robot robot = new Robot();
            Platform.runLater(() -> lbIniciadoControler.setText(andamentoEm));
            mousesList.forEach(k -> {
                System.out.println("Mudanca Clicker " + mousesList.toString());
                if(isExecutableMouseControler) {
                    for (int i = 1; i <= k.getClickers() && isExecutableMouseControler; i++) {
                        robot.mouseMove(k.getX(), k.getY());
                        robot.delay(getVelocidade(getGroupBotoesVelocidade1().getText(), true));
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                }
            });
        } catch (AWTException e){e.printStackTrace();}
    }

    //<\CLICK>-------------------------------------------------------------------------------------------------------------------------------------

    //<VERIFICACOES>------------------------------------------------------------------------------------------------------------------------------

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
                lbMostraTeclaClicker.setText("Ctrl + F1");
            }
        } else {
            if (!(tfWait.getText().equals(tfStop.getText()) || tfStop.getText().equals(tfStart.getText()) || tfStart.getText().equals(tfWait.getText()))) {
                lbStart.setText(tfStart.getText());
                lbStop.setText(tfStop.getText());
                lbPause.setText(tfWait.getText());
                lbMostraTeclaClicker.setText("F1");
            }
        }
    }

    private void atualizandoTabela() {

        tvTabela.getItems().clear();

        System.out.println(mousesList.toString());
        mousesObservableList = FXCollections.observableArrayList(mousesList);
        tvTabela.setItems(mousesObservableList);
    }

    private void atualizaComboBox() {
        if (!mousesList.isEmpty()) {
            indexMouse.clear();
            mousesList.forEach(k -> indexMouse.add(String.valueOf(k.getOrdem())));
            ObservableList<String> indexMouses = FXCollections.observableArrayList(indexMouse);
            cboxOrdem.setItems(indexMouses);
            cboxMudancaIndex.setItems(indexMouses);
        }
    }

    private void selectLingua() {
        String tutorialClickerInfinite;
        String tutorialClickerTempo;
        switch (LANGUAGE) {
            case("pt") :
                comecaEm = "Comeca em ";
                pausaEm = "Pausar";
                fimDaExecucao = "Fim da Execucao";
                andamentoEm = "Em andamento...";
                tutorialClickerInfinite = "ClickerInfinite: serve para um clicker em\n" +
                        "velocidade já feita pelo usuário e um tempo \n" +
                        "infinito de clicker. Lembre-se, ele só Start e Stop.\n" +
                        "Sem Pause.";
                tutorialClickerTempo = "Você terá 3 espacamentos h,m,s. dependendo do que colocar,\n" +
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

    @FXML
    public void limparTabela() {
        mousesList.clear();
        mousesObservableList.clear();
        indexMouse.clear();
        tvTabela.getItems().clear();
        tfClickers.setText("");
        lbIndexPara.setText("");
        cboxOrdem.getEditor().clear();
        cboxOrdem.getItems().clear();
        cboxOrdem.itemsProperty().getValue().clear();
        cboxMudancaIndex.getEditor().clear();
        cboxMudancaIndex.getItems().clear();
        cboxMudancaIndex.itemsProperty().getValue().clear();
        System.out.println("Mudanca Index " + mousesList.toString());
        atualizaComboBox();
    }

    //<\VERIFICACOES>------------------------------------------------------------------------------------------------------------------------------

    @FXML
    public void deleteIndex() {
        if(cboxOrdem.getEditor().getText() != null) {
            int indexRaiz = Integer.parseInt(cboxOrdem.getEditor().getText()) - 1;
            mousesList.remove(indexRaiz);
            for(int i = 0;mousesList.size() > i;i++) {
                mousesList.get(i).setOrdem(i + 1);
            }
            cboxOrdem.getEditor().clear();
            cboxOrdem.getItems().clear();
            lbIndexPara.setText("");
            atualizaComboBox();
            atualizandoTabela();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Detecta().retiraNumerosClicker();
        lerDados();
        atualizaTeclasMostradas();
        verificaTempoDeClicker();
        atualizandoTabela();
        selectLingua();
        try {
            GlobalScreen.registerNativeHook();
            ControleMouse cm = new ControleMouse();
            GlobalScreen.addNativeKeyListener(new ControleKeyBoard());
            GlobalScreen.addNativeMouseMotionListener(cm);
            GlobalScreen.addNativeMouseListener(cm);
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
        painelPrincipal.setStyle("-fx-background-image: url(\"Image/imagem.png\"); -fx-background-size: 100% 100%;");
    }

    //<CONTROLER>------------------------------------------------------------------------------------------------------------------------------

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
            rbClickTempo.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            rbClickerInfinite.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
            cboxOrdem.valueProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                if (newValue != null) {
                    lbIndexPara.setText("Troca de Index " + cboxOrdem.getEditor().getText() + " Para->");
                    lbDeleteIndex.setText("Delete Index " + cboxOrdem.getEditor().getText());
                    if (!newValue.matches("\\d*")) {
                        cboxOrdem.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
                    }
                    if (mousesList.get(Integer.parseInt(newValue) - 1).getClickers() != 0) {
                        tfClickers.setText(String.valueOf(mousesObservableList.get(Integer.parseInt(newValue) - 1).getClickers()));
                    } else {
                        tfClickers.setText("");
                    }
                }
            }));
            tfClickers.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tfClickers.setText(newValue.replaceAll("[^\\d]", ""));
                }
                try {
                    if(isSetClickers) {
                        mousesList.get(Integer.parseInt(cboxOrdem.getEditor().getText()) - 1).setClickers(Integer.parseInt(newValue));
                    }
                    isSetClickers = true;
                    atualizandoTabela();
                } catch (Exception ignore) {

                }
            });
            cboxMudancaIndex.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    int indexRaiz = Integer.parseInt(cboxOrdem.getEditor().getText()) - 1;
                    int indexCaminho = Integer.parseInt(newValue) - 1;
                    mousesList.get(indexRaiz).setOrdem(indexCaminho + 1);
                    mousesList.get(indexCaminho).setOrdem(indexRaiz + 1);
                    Collections.swap(mousesList, indexRaiz, indexCaminho);
                    atualizandoTabela();
                    Platform.runLater(() -> {
                        isSetClickers = false;
                        cboxOrdem.getSelectionModel().select(indexCaminho);
                        cboxMudancaIndex.getSelectionModel().clearSelection();
                    });
                    System.out.println("Mudanca Index " + mousesList.toString());
                    atualizaComboBox();
                }
            });
            rbMouseControler.selectedProperty().addListener((observable, oldValue, newValue) -> armazenaDados());
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
                if (isSetControler) {
                    mousesList.add(new Mouse(nativeMouseEvent.getX(), nativeMouseEvent.getY(), 0, mousesList.size() + 1));
                    atualizandoTabela();
                    atualizaComboBox();
                    isSetControler = false;
                }

                System.out.printf("X : %d Y : %d \n", nativeMouseEvent.getX(), nativeMouseEvent.getY());
        }

        @Override
        public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
            positionX = nativeMouseEvent.getX();
            positionY = nativeMouseEvent.getY();
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
                if (!isPause && isYesPause) {
                    isPause = true;
                    Platform.runLater(() -> lbIniciando.setText(pausaEm));
                } else if (isYesPause) {
                    isPause = false;
                }
            }
            if (cbCtrlConfirma(letra.equals(tfStop.getText()), cbCtrlOn) && !cbDesativarTeclado.isSelected()) {
                setExecutableClicker(false);
                isExecutableMouseControler = false;
                isPause = true;
                Platform.runLater(() -> pbDuracao.setProgress(0));
            }
            if(cbCtrlConfirma(letra.equals("F1"),cbCtrlOn) && !cbDesativarTeclado.isSelected()) {
                mousesList.add(new Mouse(positionX, positionY, 0, mousesList.size() + 1));
                atualizaComboBox();
                atualizandoTabela();
            }
            if (cbCtrlConfirma(letra.equals(tfStart.getText()), cbCtrlOn) && !cbDesativarTeclado.isSelected()) {
                setExecutableClicker(true);
                if(rbClickTempo.isSelected()) {
                    executaMouseTempo();
                } else if(rbClickerInfinite.isSelected()) {
                    clickerInfinite();
                } else if (rbMouseControler.isSelected()) {
                    Platform.runLater(Controlador.this::executaMouseControler);
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

        private int getTfTempoDeClicker() {
            if (tfTempoDeClicker.getText().equals("")) {
                return 0;
            } else {
                return Integer.parseInt(tfTempoDeClicker.getText());
            }
        }

        void espera(CallBackInterface listener) {
            Platform.runLater(() -> {
                lbIniciando.setVisible(true);
                pbDuracao.setVisible(true);
            });
            new Thread(() -> {
                if (cbTempoDeClicker.isSelected()) {
                    setExecutableClicker(true);
                    for (int i = getTfTempoDeClicker(); 0 <= i; i--) {
                        try {
                            int finalI = i;
                            Platform.runLater(() -> lbIniciando.setText(comecaEm + finalI + "s"));
                            Thread.sleep(1000);
                            //Stop Application for now;
                            if (!isExecutableClicker) {
                                setExecutableClicker(true);
                                break;
                            }
                        } catch (InterruptedException | RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    listener.onFinish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        void esperaControler(CallBackInterface listener) {
            Platform.runLater(() -> {
                lbIniciadoControler.setVisible(true);
                pbDuracao.setVisible(true);
            });
            new Thread(() -> {
                if (cbTempoDeClicker.isSelected()) {
                    setExecutableClicker(true);
                    for (int i = getTfTempoDeClicker(); 0 <= i; i--) {
                        try {
                            int finalIl = i;
                            Platform.runLater(() -> lbIniciadoControler.setText(comecaEm + finalIl + "s"));
                            Thread.sleep(1000);
                            //Stop Application for now;
                            if (!isExecutableClicker) {
                                setExecutableClicker(true);
                                break;
                            }
                        } catch (InterruptedException | RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    listener.onFinish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    //<\CONTROLER>------------------------------------------------------------------------------------------------------------------------------

    private CallBackEspera cb = new CallBackEspera();
}