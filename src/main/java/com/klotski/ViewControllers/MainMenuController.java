package com.klotski.ViewControllers;

import com.klotski.UI.DispositionCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private GridPane menuGrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("OK1");
    }
    public void StartGameClicked(MouseEvent event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/dispositions.fxml"));
        OpenWindow(fxmlLoader, "Dispositions", event);
    }

    public void LoadGameClicked(MouseEvent event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/saved.fxml"));
        OpenWindow(fxmlLoader, "Saved Games", event);
    }

    public void CreditsClicked(MouseEvent event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/credits.fxml"));
        OpenWindow(fxmlLoader, "Credits", event);
    }

    public void ExitClicked(MouseEvent event)
    {
        Stage stage = (Stage) menuGrid.getScene().getWindow();
        stage.close();
    }

    private void OpenWindow(FXMLLoader fxmlLoader, String title, MouseEvent event)
    {
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}