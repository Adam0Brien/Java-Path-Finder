module teamproject.ca2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens teamproject.ca2 to javafx.fxml;
    exports teamproject.ca2;
}