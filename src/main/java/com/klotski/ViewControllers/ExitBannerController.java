package com.klotski.ViewControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitBannerController implements Initializable {

    @FXML
    private AnchorPane scenePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("OK");
    }

    public void CloseClicked(ActionEvent actionEvent)
    {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void UndoClicked(ActionEvent actionEvent)
    {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }
}

