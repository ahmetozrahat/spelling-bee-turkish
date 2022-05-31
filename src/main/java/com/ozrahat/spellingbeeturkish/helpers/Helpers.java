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
