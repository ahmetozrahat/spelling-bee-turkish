package com.ozrahat.spellingbeeturkish.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateWordController {

    private ArrayList<Character> alphabet;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField charactersTextField;

    @FXML
    private Button createGameButton;

    public CreateWordController() {
        initializeAlphabet();
    }

    /**
     * Method for initializing the ArrayList with Turkish Alphabet.
     */
    private void initializeAlphabet() {
        alphabet = new ArrayList<>(List.of('a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h', 'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'r', 's', 'ş', 't', 'u', 'ü', 'v', 'y', 'z'));
    }

    private boolean isValidQuery(String query) {
        if (query.length() != 7)
            return false;
        if (!isDistinctLetters(query))
            return false;
        return isAllCharactersBelongToAlphabet(query);
    }

    @FXML
    private void createGameButtonClicked() {
        // Check whether the Text Field is empty or not.
        if (!charactersTextField.getText().isEmpty() && !charactersTextField.getText().isBlank()) {
            String query = charactersTextField.getText().trim().toLowerCase();
            if (isValidQuery(query)) {
                System.out.println("Valid query string.");
            }else {
                System.out.println("Not a valid query string.");
            }
        }
    }

    /**
     * Method for checking whether the given query string
     * contains a duplicate character or not.
     *
     * @param word The 7 character string entered by user.
     * @return true or false
     */
    public boolean isDistinctLetters(String word) {
        HashMap<Character, Integer> characters = new HashMap<>();
        for (Character character: word.toCharArray()) {
            characters.merge(character, 1, Integer::sum);
        }

        for (Character character: word.toCharArray()) {
            if (characters.get(character) > 1)
                return false;
        }
        return true;
    }

    /**
     * Method for checking whether all the characters of the query
     * belongs to the Turkish alphabet or not.
     *
     * @param query The 7 character string entered by user.
     * @return true or false
     */
    public boolean isAllCharactersBelongToAlphabet(String query) {
        for (Character character: query.toCharArray()) {
            if (!alphabet.contains(character))
                return false;
        }
        return true;
    }
}
