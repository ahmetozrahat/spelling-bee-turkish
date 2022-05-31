package com.ozrahat.spellingbeeturkish.model;

import com.ozrahat.spellingbeeturkish.helpers.Listener;

import java.util.*;

public class GameModel {
    private List<Listener> listeners = new LinkedList<>();

    private ArrayList<Character> characters;
    private Character centerCharacter;

    private TreeSet<String> wordList;
    private TreeSet<String> correctAnswers;

    private int score;

    public GameModel() {
        characters = new ArrayList<>();
        correctAnswers = new TreeSet<>();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        notifyObservers();
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public Character getCenterLetter() {
        return centerCharacter;
    }

    public void setCenterLetter(Character centerLetter) {
        this.centerCharacter = centerLetter;
    }

    public TreeSet<String> getWordList() {
        return wordList;
    }

    public void setWordList(TreeSet<String> wordList) {
        this.wordList = wordList;
    }

    public TreeSet<String> getCorrectAnswers() {
        return correctAnswers;
    }

    private void notifyObservers() {
        listeners.forEach(listener -> listener.onChange(this));
    }

    public void shuffleLetters() {
        Collections.shuffle(characters);
        notifyObservers();
    }

    public void addCorrectAnswer(String word) {
        correctAnswers.add(word);
        notifyObservers();
    }
}
