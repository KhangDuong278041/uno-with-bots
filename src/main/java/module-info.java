module com.jungko {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.jungko to javafx.fxml;
    opens com.jungko.controller to javafx.fxml;
    exports com.jungko;
    exports com.jungko.controller;
}