module com.ozrahat.spellingbeeturkish {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ozrahat.spellingbeeturkish to javafx.fxml;
    exports com.ozrahat.spellingbeeturkish;
    exports com.ozrahat.spellingbeeturkish.controller;
    opens com.ozrahat.spellingbeeturkish.controller to javafx.fxml;
}