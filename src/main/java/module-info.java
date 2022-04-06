module teamproject.ca2 {
    requires javafx.controls;
    requires javafx.fxml;

    exports main;
    opens main to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
    exports utils;
    opens utils to javafx.fxml;
    exports model;
    opens model to javafx.fxml;
}