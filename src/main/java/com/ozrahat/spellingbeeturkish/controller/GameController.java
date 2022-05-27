package com.ozrahat.spellingbeeturkish.controller;

import com.ozrahat.spellingbeeturkish.helpers.Helpers;
import com.ozrahat.spellingbeeturkish.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GameController {

    GameModel gameModel;

    @FXML
    private TextField textField;

    @FXML
    private Button letterButton1;

    @FXML
    private Button letterButton2;

    @FXML
    private Button letterButton3;

    @FXML
    private Button letterButton4;

    @FXML
    private Button letterButton5;

    @FXML
    private Button letterButton6;

    @FXML
    private Button centerLetterButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button shuffleButton;

    @FXML
    private Button enterButton;

    @FXML
    private Label scoreLabel;

    @FXML
    private ProgressBar scoreProgressBar;

    @FXML
    private Label wordsLabel;

    @FXML
    private ListView<Label> wordsListView;

    public GameController() {
    }

    @FXML
    private void textFieldEntered() {
        if (!textField.getText().isEmpty() && !textField.getText().isBlank())
            System.out.println(textField.getText().trim());
    }

    @FXML
    private void letterButton1Clicked() {
        textField.setText(textField.getText() + letterButton1.getText());
    }

    @FXML
    private void letterButton2Clicked() {
        textField.setText(textField.getText() + letterButton2.getText());
    }

    @FXML
    private void letterButton3Clicked() {
        textField.setText(textField.getText() + letterButton3.getText());;
    }

    @FXML
    private void letterButton4Clicked() {
        textField.setText(textField.getText() + letterButton4.getText());
    }

    @FXML
    private void letterButton5Clicked() {
        textField.setText(textField.getText() + letterButton5.getText());
    }

    @FXML
    private void letterButton6Clicked() {
        textField.setText(textField.getText() + letterButton6.getText());
    }

    @FXML
    private void centerLetterButtonClicked() {
        textField.setText(textField.getText() + centerLetterButton.getText());
    }

    @FXML
    private void deleteButtonClicked() {
        // Check for whether TextField is empty or not.
        if (!textField.getText().trim().isEmpty() && !textField.getText().trim().isBlank()) {
            String sequence = textField.getText().trim();
            String newSequence = Helpers.removeLastCharacter(sequence);
            textField.setText(newSequence);
        }
    }

    @FXML
    private void shuffleButtonClicked() {
        System.out.println("shuffle");
    }

    @FXML
    private void enterButtonClicked() {
        System.out.println("enter");
    }

    public void setModel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addListener(m -> scoreLabel.setText("Puanınız: " + m.getScore()));
    }
}