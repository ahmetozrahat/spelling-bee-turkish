package com.ozrahat.spellingbeeturkish.controller;

import com.ozrahat.spellingbeeturkish.Main;
import com.ozrahat.spellingbeeturkish.helpers.Helpers;
import com.ozrahat.spellingbeeturkish.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class HomeController {

    @FXML
    private Button autoGenerateButton;

    @FXML
    private Button manualGenerateButton;

    private FXMLLoader fxmlLoader;

    public HomeController() {
        fxmlLoader = new FXMLLoader();
    }

    @FXML
    private void autoGenerateButtonClicked() {
        fxmlLoader.setLocation(Main.class.getResource("game-view.fxml"));
        try {
            Parent root = fxmlLoader.load();

            GameModel gameModel = new GameModel();
            GameController gameController = fxmlLoader.getController();
            gameController.setModel(gameModel);

            Helpers.pushView(root, autoGenerateButton);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to open game window.");
        }
    }

    @FXML
    private void manualGenerateButtonClicked() {
        fxmlLoader.setLocation(Main.class.getResource("create-word-view.fxml"));
        try {
            Parent root = fxmlLoader.load();

            Helpers.pushView(root, manualGenerateButton);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to open game window.");
        }
    }
}
