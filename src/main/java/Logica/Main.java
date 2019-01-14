package Logica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;


/**
 * @Author: Jonatas De Oliveira Ferreira
 * @Version: : 0.7 -> 0.8
 *
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Grafica/sample.fxml"), ResourceBundle.getBundle("limguages"));
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("/Image/ClickerImage.png"));
            stage.setTitle("Click Never 0.8");

            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.show();
            alert.setHeaderText(e.getLocalizedMessage());
            alert.setContentText(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        Controlador c = new Controlador();
        c.setExecutableClicker(false);
        System.exit(0);
    }
}
