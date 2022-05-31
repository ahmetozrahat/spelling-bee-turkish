package com.ozrahat.spellingbeeturkish.controller;

import com.ozrahat.spellingbeeturkish.Main;
import com.ozrahat.spellingbeeturkish.helpers.Helpers;
import com.ozrahat.spellingbeeturkish.helpers.SpellingBeeGameBuilder;
import com.ozrahat.spellingbeeturkish.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateWordController {

    @FXML
    private Label infoLabel;

    @FXML
    private TextField charactersTextField;

    @FXML
    private Button createGameButton;

    private FXMLLoader fxmlLoader;

    public CreateWordController() {
        fxmlLoader = new FXMLLoader();
    }

    @FXML
    private void createGameButtonClicked() {
        // Check whether the Text Field is empty or not.
        if (!charactersTextField.getText().isEmpty() && !charactersTextField.getText().isBlank()) {
            String query = charactersTextField.getText().trim().toLowerCase();

            SpellingBeeGameBuilder spellingBeeGameBuilder = new SpellingBeeGameBuilder();
            try {
                GameModel gameModel = spellingBeeGameBuilder.buildGame(query);
                startGame(gameModel);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void startGame(GameModel gameModel) {
        fxmlLoader.setLocation(Main.class.getResource("game-view.fxml"));
        try {
            Parent root = fxmlLoader.load();

            GameController gameController = fxmlLoader.getController();
            gameController.setModel(gameModel);

            Helpers.pushView(root, createGameButton);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to open game window.");
        }
    }
}
