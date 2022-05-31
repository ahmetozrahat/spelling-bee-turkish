package com.ozrahat.spellingbeeturkish.helpers;

import com.ozrahat.spellingbeeturkish.Main;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.*;

public class Helpers {

    /**
     * Removes the last character from string.
     *
     * @param word Any given string.
     * @return String with trimmed last character.
     */
    public static String removeLastCharacter(String word) {
        StringBuilder charSequence = new StringBuilder(word);
        charSequence.deleteCharAt(charSequence.length() - 1);
        return charSequence.toString();
    }

    /**
     * Method for checking whether a given word is pangram or not.
     * <strong>Pangram: </strong> A word that contains all the given characters.
     * @param word Any given word.
     * @param characters All 7 characters to create the game.
     * @return true or false
     */
    public static boolean isPangram(String word, ArrayList<Character> characters) {
        // Create a copy of the characters array to manipulate.
        ArrayList<Character> newLetters = new ArrayList<>(characters);
        // For each character of the given word,
        // check whether the characters array contains it or not.
        for (Character character: word.toLowerCase().toCharArray()) {
            if (characters.contains(character))
                // If the characters exists, remove it from the copy array.
                newLetters.remove(character);
            else
                return false;
        }
        // If the copy array is empty,
        // it means that each character exists in the characters array.
        // Hence it's a pangram word.
        return newLetters.isEmpty();
    }

    /**
     * Method for checking whether a word has 7 distinct characters.
     *
     * @param word Any given word.
     * @return true or false
     */
    public static boolean canPangram(String word) {
        if (word.length() < 7)
            return false;
        HashMap<Character, Integer> characters = new HashMap<>();
        for (Character character: word.toCharArray()) {
            characters.merge(character, 1, Integer::sum);
        }
        return characters.keySet().size() == 7;
    }

    /**
     * Method for finding whether a word is valid or not.
     * Rules are:
     * <ul>
     * <li>Each word must be at least four letters long</li>
     * <li>Each word must not contain any letters other than the seven letters</li>
     * </ul>
     *
     * @param word Any given word.
     * @param characters All 7 characters to create the game.
     * @return true or false
     */
    public static boolean isValidWord(String word, ArrayList<Character> characters) {
        // If the word is less than 4 characters long, return false.
        if (word.length() < 4)
            return false;
        // Check if word contains all the characters.
        // If not so, return false.
        for (Character character: word.toLowerCase().toCharArray()) {
            if (!characters.contains(character))
                return false;
        }
        return true;
    }

    /**
     * Method for finding the best character to put it into the center of the hive.
     * It finds the most frequent character in the array.
     *
     * @param characters The characters selected to start the game.
     * @param words Words made up with given characters.
     * @return Character that most frequent in all the given words.
     */
    public static Character getWordCountByCenterCharacter(ArrayList<Character> characters, ArrayList<String> words) {
        // First create a hash map to hold that
        // how many times each character found in the words array.
        HashMap<Character, Integer> letterCounts = new HashMap<>();
        // Initialize the hashmap with putting each character with a value of 0.
        for (Character letter: characters) {
            letterCounts.put(letter, 0);
        }

        // Check all characters for each word so that if they exist in the word or not.
        // If so increment the value of the character by one in the hashmap.
        for (String word: words) {
            for (Character letter: characters) {
                if (word.contains(String.valueOf(letter)))
                    letterCounts.put(letter, letterCounts.get(letter) + 1);
            }
        }
        // Find the most frequent character and return it.
        return Collections.max(letterCounts.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    /**
     * Method for checking all the possible words that contains
     * the given center character at least one.
     *
     * @param words Words made up with given characters.
     * @param centerCharacter The character that must be existed in every word at least one.
     * @return Array of the word list to start the game
     */
    public static ArrayList<String> getWordListForGame(ArrayList<String> words, Character centerCharacter) {
        // Initialize an array to hold the words.
        ArrayList<String> wordList = new ArrayList<>();
        // We need to get all the possible words which contains the center character.
        for (String word: words) {
            if (word.contains(String.valueOf(centerCharacter)))
                wordList.add(word.toLowerCase());
        }
        return wordList;
    }

    /**
     * Method for checking all the possible words that contains
     * the given center character at least one.
     *
     * @param words Words made up with given characters.
     * @param characters The character list that must be existed in every word.
     * @return Array of the word list to start the game
     */
    public static ArrayList<String> getWordListForGame(ArrayList<String> words, ArrayList<Character> characters, Character centerCharacter) {
        // Initialize an array to hold the words.
        ArrayList<String> wordList = new ArrayList<>();
        // We need to get all the possible words which contains the center character.
        for (String word: words) {
            if (isValidWord(word, characters) && word.contains(String.valueOf(centerCharacter)))
                wordList.add(word);
        }
        return wordList;
    }

    public static void pushView(Parent root, Button manualGenerateButton) {
        Scene scene = new Scene(root, 960, 540);
        Stage stage = new Stage();

        Image image = new Image(Main.class.getResourceAsStream("bee.png"));
        stage.getIcons().add(image);

        stage.setTitle("Spelling Bee Turkish");
        stage.setScene(scene);
        stage.show();

        manualGenerateButton.getScene().getWindow().hide();
    }
}
