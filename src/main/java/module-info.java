module com.klotski {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    opens com.klotski to javafx.fxml;
    opens com.klotski.View to javafx.fxml;
    exports com.klotski;
}