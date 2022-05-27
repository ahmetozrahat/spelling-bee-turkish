package com.ozrahat.spellingbeeturkish.controller;

import com.ozrahat.spellingbeeturkish.Main;
import com.ozrahat.spellingbeeturkish.helpers.Dictionary;
import com.ozrahat.spellingbeeturkish.helpers.Helpers;
import com.ozrahat.spellingbeeturkish.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.io.File;

public class GameController {

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

    private GameModel gameModel;
    private Dictionary dictionary;

    public GameController() {
        File dictionaryFile = new File("C:\\Users\\ahmet\\IdeaProjects\\SpellingBeeTurkish\\src\\main\\java\\com\\ozrahat\\spellingbeeturkish\\assets\\dictionary.txt");
        dictionary = new Dictionary(dictionaryFile);
        dictionary.printWords();
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
        gameModel.shuffleLetters();
    }

    @FXML
    private void enterButtonClicked() {
        // Check for whether TextField is empty or not.
        if (!textField.getText().trim().isEmpty() && !textField.getText().trim().isBlank()) {
            String word = textField.getText().trim().toLowerCase();
            if (isWordValid(word)) {
                textField.clear();
                gameModel.addCorrectAnswer(word);
                gameModel.setScore(gameModel.getScore() + (word.length() - 3));
            }
        }
    }

    public void setModel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addListener(m -> {
            scoreLabel.setText("Puanınız: " + m.getScore());
            scoreProgressBar.setProgress(gameModel.getScore() / 100.0);
            wordsLabel.setText("Toplamda " + m.getCorrectAnswers().size() + " kelime buldunuz.");

            letterButton1.setText(gameModel.getLetters().get(0));
            letterButton2.setText(gameModel.getLetters().get(1));
            letterButton3.setText(gameModel.getLetters().get(2));
            letterButton4.setText(gameModel.getLetters().get(3));
            letterButton5.setText(gameModel.getLetters().get(4));
            letterButton6.setText(gameModel.getLetters().get(5));

            wordsListView.getItems().clear();
            gameModel.getCorrectAnswers().forEach(word -> {
                Label label = new Label(word);
                label.setFont(Font.loadFont(Main.class.getResourceAsStream("fonts/Poppins-Medium.ttf"),14));
                wordsListView.getItems().add(label);
            });
        });
    }

    /**
     * Method for checking whether the given word is valid and
     * exists in the dictionary or not.
     *
     * @param word Ayn given word.
     * @return true or false
     */
    public boolean isWordValid(String word) {
        if (word.length() < 4)
            return false;
        if (!word.contains(gameModel.getCenterLetter().toLowerCase()))
            return false;
        if (gameModel.getCorrectAnswers().contains(word))
            return false;
        return dictionary.wordExists(word);
    }
}