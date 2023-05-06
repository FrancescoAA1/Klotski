package com.klotski.ViewControllers;

import com.klotski.UI.DispositionCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DispositionsListController implements Initializable {
    /*@FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/

    @FXML
    private GridPane grid;
    public DispositionsListController()
    {
        System.out.println("OK");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {System.out.println("OK1");
        DispositionCard card1 = new DispositionCard(getClass().getResource("/com/klotski/Images/m1.png").getPath(), 1);
        DispositionCard card2 = new DispositionCard(getClass().getResource("/com/klotski/Images/m2.png").getPath(), 2);
        DispositionCard card3 = new DispositionCard(getClass().getResource("/com/klotski/Images/m3.png").getPath(), 3);
        Pane cardpage = card1.GetControl();
        grid.add(cardpage, 0,0);
        GridPane.setMargin(cardpage, new Insets(16,6,6,6));
        cardpage = card2.GetControl();
        grid.add(cardpage, 0,1);
        GridPane.setMargin(cardpage, new Insets(16,6,6,6));
        cardpage = card3.GetControl();
        grid.add(cardpage, 1,0);
        GridPane.setMargin(cardpage, new Insets(16,6,6,6));
    }
}