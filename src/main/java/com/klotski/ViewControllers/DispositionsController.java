package com.klotski.ViewControllers;

import com.klotski.UI.DispositionCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DispositionsController implements Initializable {
    /*@FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/

    @FXML
    private GridPane grid;
    public DispositionsController()
    {
        System.out.println("OK");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {System.out.println("OK1");
        DispositionCard card = new DispositionCard(getClass().getResource("/com/klotski/Images/miniatura.png").getPath(), 1);System.out.println("OK2");
        Pane cardpage = card.GetControl();
        grid.add(cardpage, 1,1);
        GridPane.setMargin(cardpage, new Insets(16,6,6,6));
    }
}