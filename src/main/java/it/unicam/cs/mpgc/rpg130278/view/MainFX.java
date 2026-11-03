package it.unicam.cs.mpgc.rpg130278.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("principale.fxml"));
        Scene scena = new Scene(loader.load(), 800, 600);
        scena.getStylesheets().add(getClass().getResource("stile.css").toExternalForm());
        stage.setTitle("La Torre dell'Alchimista");
        stage.setScene(scena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}