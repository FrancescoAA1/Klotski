package com.klotski;

import com.klotski.Controllers.NextMoveGateway;
import com.klotski.Model.Disposition;
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

        //Show initial stage
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
        NextMoveGateway next = new NextMoveGateway();
        Disposition disp = new Disposition(false);
        disp.setTextDisposition("2-1-0;0#2-2-0;3#2-1-2;0#2-1-1;0#1-2-2;3#2-1-3;0#1-1-1;2#1-1-3;2#1-1-2;4#1-1-3;4#1-1-0;2#1-1-2;2");
        next.GetNextMove(1, disp);
        launch();
    }
}