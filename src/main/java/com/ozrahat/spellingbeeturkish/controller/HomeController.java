package com.ozrahat.spellingbeeturkish.controller;

import com.ozrahat.spellingbeeturkish.Main;
import com.ozrahat.spellingbeeturkish.helpers.Dictionary;
import com.ozrahat.spellingbeeturkish.helpers.Helpers;
import com.ozrahat.spellingbeeturkish.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.File;
import java.util.*;

public class HomeController {

    @FXML
    private Button autoGenerateButton;

    @FXML
    private Button manualGenerateButton;

    private FXMLLoader fxmlLoader;

    private Dictionary dictionary;

    private static ArrayList<String> pangramWords;

    public HomeController() {
        fxmlLoader = new FXMLLoader();
        pangramWords = new ArrayList<>();

        initializeDictionary();
    }

    /**
     * Method for initializing the Dictionary object with absolute path of the dictionary file.
     */
    private void initializeDictionary() {
        File file = new File("C:\\Users\\ahmet\\IdeaProjects\\SpellingBeeTurkish\\src\\main\\java\\com\\ozrahat\\spellingbeeturkish\\assets\\dictionary.txt");
        dictionary = new Dictionary(file);
    }

    private ArrayList<String> findPangramWords() {
        ArrayList<String> pangramWords = new ArrayList<>();
        for (String word: dictionary.getWordList()) {
            if (Helpers.canPangram(word))
                pangramWords.add(word);
        }
        return pangramWords;
    }

    private List<Character> getCharacters(String word) {
        // First create a hash map to hold that
        // how many times each character found in the word.
        Map<Character, Integer> characterCounts = new TreeMap<>();
        // Initialize the hashmap with putting each character with a value of 0.
        for (Character character: word.toCharArray()) {
            characterCounts.put(character, 0);
        }

        // Check all characters for the word.
        for (Character character: word.toCharArray()) {
            if (word.contains(String.valueOf(character)))
                characterCounts.put(character, characterCounts.get(character) + 1);
        }
        return characterCounts.keySet().stream().toList();
    }

    @FXML
    private void autoGenerateButtonClicked() {
        fxmlLoader.setLocation(Main.class.getResource("game-view.fxml"));
        try {
            Parent root = fxmlLoader.load();

            pangramWords = findPangramWords();
            Random random = new Random();
            int index = random.nextInt(pangramWords.size() - 1);

            String selectedWord = pangramWords.get(index);
            List<Character> characters = getCharacters(selectedWord);
            System.out.println("Selected word: " + selectedWord);
            System.out.println("Characters: " + characters);

            ArrayList<Character> newCharacters = new ArrayList<>(characters);
            Character centerCharacter = Helpers.getWordCountByCenterCharacter(newCharacters, dictionary.getWordList());
            System.out.println("Center character is: " + centerCharacter);
            List<String> wordList = Helpers.getWordListForGame(dictionary.getWordList(), newCharacters, centerCharacter);
            System.out.println("Found " + wordList.size() + " words to create the game.");
            System.out.println("Word list: " + wordList);
            newCharacters.remove(centerCharacter);

            GameModel gameModel = new GameModel();
            gameModel.setCharacters(newCharacters);
            gameModel.setCenterLetter(centerCharacter);
            gameModel.setWordList(new ArrayList<>(wordList));

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
