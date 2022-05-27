package com.ozrahat.spellingbeeturkish.model;

import com.ozrahat.spellingbeeturkish.helpers.Listener;

import java.util.LinkedList;
import java.util.List;

public class GameModel {
    private List<Listener> listeners = new LinkedList<>();

    private int score;

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

    private void notifyObservers() {
        listeners.forEach(listener -> listener.onChange(this));
    }
}
