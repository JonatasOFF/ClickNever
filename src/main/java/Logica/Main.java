package Logica;

import com.sun.xml.internal.ws.api.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;

/**
 * @Author: Jonatas De Oliveira Ferreira
 *
 * @Version: 0.3
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/grafica/sample.fxml"));

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/Image/ClickerImage.png"));
        stage.setTitle("Clicker Never 0.3");

        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();

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
