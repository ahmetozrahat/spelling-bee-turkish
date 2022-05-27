package com.ozrahat.spellingbeeturkish.helpers;

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
}
