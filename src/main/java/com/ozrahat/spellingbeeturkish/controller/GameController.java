package com.ozrahat.spellingbeeturkish.controller;

import com.ozrahat.spellingbeeturkish.Main;
import com.ozrahat.spellingbeeturkish.helpers.Dictionary;
import com.ozrahat.spellingbeeturkish.helpers.Helpers;
import com.ozrahat.spellingbeeturkish.model.GameModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> textField.requestFocus());
        textField.setTextFormatter(new TextFormatter<>(change -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
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
        textField.requestFocus();
    }

    @FXML
    private void shuffleButtonClicked() {
        gameModel.shuffleLetters();
        textField.requestFocus();
    }

    @FXML
    private void enterButtonClicked() {
        // Check for whether TextField is empty or not.
        if (!textField.getText().trim().isEmpty() && !textField.getText().trim().isBlank()) {
            String word = textField.getText().trim().toLowerCase();
            if (isWordValid(word)) {
                textField.clear();
                gameModel.addCorrectAnswer(word);
                gameModel.setScore(gameModel.getScore() + getPoints(word));
            }
        }
        textField.requestFocus();
    }

    public void setModel(GameModel gameModel) {
        this.gameModel = gameModel;
        updateUI(gameModel);
        gameModel.addListener(this::updateUI);
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
        if (!word.contains(String.valueOf(gameModel.getCenterLetter())))
            return false;
        if (gameModel.getCorrectAnswers().contains(word))
            return false;
        for (Character character: word.toCharArray()) {
            String characterString = String.valueOf(character);
            if (!gameModel.getCharacters().contains(characterString.charAt(0)) && !gameModel.getCenterLetter().equals(character)) {
                return false;
            }
        }
        return dictionary.wordExists(word);
    }

    /**
     * Method for calculating the correct answer points.
     *
     * @param word The found word.
     * @return point value.
     */
    public int getPoints(String word) {
        ArrayList<Character> characters = new ArrayList<>(gameModel.getCharacters());
        characters.add(gameModel.getCenterLetter());
        if (Helpers.isPangram(word, characters))
            return 7;
        return word.length() - 3;
    }

    private void updateUI(GameModel m) {
        scoreLabel.setText("Puanınız: " + m.getScore());
        scoreProgressBar.setProgress(gameModel.getScore() / 100.0);
        wordsLabel.setText("Toplamda " + m.getCorrectAnswers().size() + " kelime buldunuz.");

        letterButton1.setText(gameModel.getCharacters().get(0).toString().toUpperCase());
        letterButton2.setText(gameModel.getCharacters().get(1).toString().toUpperCase());
        letterButton3.setText(gameModel.getCharacters().get(2).toString().toUpperCase());
        letterButton4.setText(gameModel.getCharacters().get(3).toString().toUpperCase());
        letterButton5.setText(gameModel.getCharacters().get(4).toString().toUpperCase());
        letterButton6.setText(gameModel.getCharacters().get(5).toString().toUpperCase());
        centerLetterButton.setText(gameModel.getCenterLetter().toString().toUpperCase());

        wordsListView.getItems().clear();
        gameModel.getCorrectAnswers().forEach(word -> {
            Label label = new Label(word);
            label.setFont(Font.loadFont(Main.class.getResourceAsStream("fonts/Poppins-Medium.ttf"),14));
            wordsListView.getItems().add(label);
        });
    }
}