package com.klotski;

import com.klotski.Controllers.DBConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Victory extends Application{

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/win.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Klotski");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        DBConnector test = new DBConnector();
        test.Connect();
        launch();
    }
}