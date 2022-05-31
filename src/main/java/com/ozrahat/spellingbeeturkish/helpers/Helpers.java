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
