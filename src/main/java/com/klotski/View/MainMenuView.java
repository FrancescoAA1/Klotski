package com.klotski.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuView{
    @FXML
    private GridPane menuGrid;

    /* MENU BUTTONS EVENT HANDLER */
    public void StartGameClicked(MouseEvent event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/dispositions.fxml"));
        OpenWindow(fxmlLoader, "Dispositions", event);
    }

    public void LoadGameClicked(MouseEvent event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/saved.fxml"));
        OpenWindow(fxmlLoader, "Saved Games", event);
    }

    public void CreditsClicked(MouseEvent event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/credits.fxml"));
        OpenWindow(fxmlLoader, "Credits", event);
    }

    public void ExitClicked(MouseEvent event)
    {
        javafx.application.Platform.exit();
    }

    /** Open window.
     * @param fxmlLoader window loader.
     * @param title title of the new window.
     */
    private void OpenWindow(FXMLLoader fxmlLoader, String title, MouseEvent event)
    {
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Set scene and parameters
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);

        // Calculate the center position of the screen
        Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBound.getWidth() - scene.getWidth())/2;
        double centerY = (screenBound.getHeight() - scene.getHeight())/2;

        // Set the scene position to the center of the screen
        stage.setX(centerX);
        stage.setY(centerY);

        //Show current stage
        stage.show();
    }
}