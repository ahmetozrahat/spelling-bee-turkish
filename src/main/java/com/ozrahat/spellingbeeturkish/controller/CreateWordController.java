package com.ozrahat.spellingbeeturkish.controller;

import com.ozrahat.spellingbeeturkish.Main;
import com.ozrahat.spellingbeeturkish.helpers.Dictionary;
import com.ozrahat.spellingbeeturkish.helpers.Helpers;
import com.ozrahat.spellingbeeturkish.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
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

    private static Dictionary dictionary;

    private static ArrayList<String> pangramWords;
    private static ArrayList<Character> characters;

    private static ArrayList<String> validWords;
    private static ArrayList<String> wordList;

    private FXMLLoader fxmlLoader;

    public CreateWordController() {
        fxmlLoader = new FXMLLoader();
        characters = new ArrayList<>();
        pangramWords = new ArrayList<>();
        validWords = new ArrayList<>();

        initializeDictionary();
        initializeAlphabet();
    }

    /**
     * Method for initializing the Dictionary object with absolute path of the dictionary file.
     */
    private static void initializeDictionary() {
        File file = new File("C:\\Users\\ahmet\\IdeaProjects\\KelimeBulucu\\src\\com\\ozrahat\\kelimebulucu\\dictionary.txt");
        dictionary = new Dictionary(file);
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
                // Start the game.
                GameModel gameModel = new GameModel();
                for (Character character: query.toCharArray())
                    characters.add(character);

                boolean canCreateWordList = tryToFindWordList();

                if (canCreateWordList) {
                    for (String word: dictionary.getWordList()) {
                        if (Helpers.isValidWord(word, characters)) {
                            validWords.add(word);
                        }
                    }
                    Character centerCharacter =  Helpers.getWordCountByCenterCharacter(characters, validWords);
                    characters.remove(centerCharacter);
                    gameModel.setCharacters(characters);
                    gameModel.setCenterLetter(centerCharacter);

                    // Let's create our word list for the game!
                    wordList = Helpers.getWordListForGame(validWords, centerCharacter);
                    gameModel.setWordList(wordList);
                    startGame(gameModel);
                }else {
                    System.out.println("Can't create a word list with the given letters.");
                }
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

    private static boolean tryToFindWordList() {
        pangramWords.clear();
        for (String word: dictionary.getWordList()) {
            if (Helpers.isPangram(word, characters)) {
                pangramWords.add(word);
                System.out.println(word + " is a pangram!");
            }
        }
        return !pangramWords.isEmpty();
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
