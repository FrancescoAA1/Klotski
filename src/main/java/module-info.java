module com.klotski {
    requires javafx.fxml;
    requires javafx.controls;
            
                            
    opens com.klotski to javafx.fxml;
    opens com.klotski.ViewControllers to javafx.fxml;
    exports com.klotski;
}