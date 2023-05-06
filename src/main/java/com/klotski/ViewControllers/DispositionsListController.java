package com.klotski.ViewControllers;

import com.klotski.UI.DispositionCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
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
    {
        ArrayList<DispositionCard> cards = new ArrayList<>();
        cards.add(new DispositionCard(getClass().getResource("/com/klotski/Images/m1.png").getPath(), 1));
        cards.add(new DispositionCard(getClass().getResource("/com/klotski/Images/m2.png").getPath(), 2));
        cards.add(new DispositionCard(getClass().getResource("/com/klotski/Images/m3.png").getPath(), 3));
        cards.add(new DispositionCard(getClass().getResource("/com/klotski/Images/m1.png").getPath(), 4));
        cards.add(new DispositionCard(getClass().getResource("/com/klotski/Images/m2.png").getPath(), 5));
        cards.add(new DispositionCard(getClass().getResource("/com/klotski/Images/m3.png").getPath(), 16));

        int x = 0;
        int y = 0;
        for(int i = 0; i < cards.size(); i++)
        {
            grid.add(cards.get(i).GetControl(), x++,y);
            GridPane.setMargin(cards.get(i).GetControl(), new Insets(16,6,6,6));
            if(x >= 3)
            {
                x = 0;
                y++;
            }
        }
    }
}