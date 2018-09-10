package Logica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @Author: Jonatas De Oliveira Ferreira
 * @Version: 0.3
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Grafica/sample.fxml"),ResourceBundle.getBundle("limguages"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("/Image/ClickerImage.png"));
            stage.setTitle("Clicker Never 0.4 - SNAPSHOT");

            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao iniciar");
            alert.setContentText(e.getMessage());
            alert.setHeaderText(e.getLocalizedMessage());
            alert.show();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        Controlador c = new Controlador();
        c.setPodeExecutarClicke(false);
        System.exit(0);
    }
}
