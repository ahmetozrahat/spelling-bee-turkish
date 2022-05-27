package com.ozrahat.spellingbeeturkish;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));

        Image image = new Image(Main.class.getResourceAsStream("bee.png"));
        stage.getIcons().add(image);

        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        stage.setTitle("Spelling Bee Turkish");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}