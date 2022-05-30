package com.ozrahat.spellingbeeturkish.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    private final ArrayList<String> wordList;

    /**
     * Constructor for Dictionary class that takes a file as parameter
     * and reads calls the readFile method and initializes the wordList array.
     *
     * @param dictionaryFile Dictionary file that contains the word list.
     */
    public Dictionary(File dictionaryFile) {
        this.wordList = new ArrayList<>();
        readFile(dictionaryFile);
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    /**
     * Method for reading the words into the array list.
     *
     * @param file Dictionary file.
     */
    private void readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + e.getLocalizedMessage());
        }
    }

    /**
     * Method for displaying all the words fetched into the array list.
     */
    public void printWords() {
        for (String word: wordList) {
            System.out.println(word);
        }
    }

    /**
     * Method for checking whether the given word exists in the wordlist or not.
     *
     * @param word Any given word.
     * @return true or false
     */
    public boolean wordExists(String word) {
        return wordList.contains(word);
    }
}
