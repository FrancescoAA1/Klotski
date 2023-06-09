package com.klotski.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.awt.*;

public class CreditsView {

    /* BUTTON EVENT HANDLER */

    public void MenuClicked(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/menu.fxml"));
        OpenWindow(fxmlLoader, "Main Menu", actionEvent);
    }

    /** Open main menu window.
     * @param fxmlLoader window loader.
     * @param title title of the new window.
     */
    private void OpenWindow(FXMLLoader fxmlLoader, String title, ActionEvent event)
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

        // Show current stage
        stage.show();
    }

    public void topLeftProfileClick(ActionEvent event) throws URISyntaxException, IOException
    {
        Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/tommaso-danieli-520700278"));
    }

    public void topRightProfileClick(ActionEvent event) throws URISyntaxException, IOException
    {
        Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/francesco-fantin-2819801a2/"));
    }

    public void downLeftProfileClick(ActionEvent event) throws URISyntaxException, IOException
    {
        Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/mattia-galassi-a624b4172/"));
    }

    public void downRightProfileClick(ActionEvent event) throws URISyntaxException, IOException
    {
        Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/tomas-lovato-34783a162/"));
    }
}