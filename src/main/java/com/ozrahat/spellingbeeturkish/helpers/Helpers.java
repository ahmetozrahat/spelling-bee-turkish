package com.ozrahat.spellingbeeturkish.helpers;

import com.ozrahat.spellingbeeturkish.model.GameModel;

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
     * Method to indicate that whether the given word is pangram or not.
     *
     * @param word The word.
     * @param gameModel GameModel object.
     * @return true or false.
     */
    public static boolean isPangram(String word, GameModel gameModel) {
        if (word.length() == gameModel.getLetters().size() + 1) {
            for (int i = 0; i < word.length(); i++) {
                String formattedChar = String.valueOf(word.charAt(i)).toUpperCase();
                if (!gameModel.getLetters().contains(formattedChar) && !gameModel.getCenterLetter().equals(formattedChar))
                    return false;
            }
            return true;
        }else {
            return false;
        }
    }
}
