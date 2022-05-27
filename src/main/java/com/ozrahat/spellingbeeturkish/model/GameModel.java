package com.ozrahat.spellingbeeturkish.model;

import com.ozrahat.spellingbeeturkish.helpers.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameModel {
    private List<Listener> listeners = new LinkedList<>();

    private ArrayList<String> letters;
    private String centerLetter;

    private int score;

    public GameModel() {
        letters = new ArrayList<>();
        letters.add("A");
        letters.add("D");
        letters.add("C");
        letters.add("H");
        letters.add("E");
        letters.add("G");
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

    public ArrayList<String> getLetters() {
        return letters;
    }

    public void setLetters(ArrayList<String> letters) {
        this.letters = letters;
    }

    public String getCenterLetter() {
        return centerLetter;
    }

    public void setCenterLetter(String centerLetter) {
        this.centerLetter = centerLetter;
    }

    private void notifyObservers() {
        listeners.forEach(listener -> listener.onChange(this));
    }

    public void shuffleLetters() {
        Collections.shuffle(letters);
        notifyObservers();
    }
}
