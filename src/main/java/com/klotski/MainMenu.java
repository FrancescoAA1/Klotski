package com.klotski;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Klotski");
        stage.getIcons().add(new Image(MainMenu.class.getResourceAsStream("/com/klotski/Assets/icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Calculate the center position of the screen
        Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBound.getWidth() - scene.getWidth())/2;
        double centerY = (screenBound.getHeight() - scene.getHeight())/2;

        // Set the scene position to the center of the screen
        stage.setX(centerX);
        stage.setY(centerY);
    }

    public static void main(String[] args) {
        launch();
    }
}