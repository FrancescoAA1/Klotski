module com.example.klotski {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.klotski to javafx.fxml;
    exports com.example.klotski;
}