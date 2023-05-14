package com.klotski.View;

import com.klotski.Controllers.DBConnector;
import com.klotski.Controllers.GameHandler;
import com.klotski.UI.DispositionCard;
import com.klotski.model.Disposition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DispositionsListView implements Initializable
{
    @FXML
    private GridPane grid;
    private ArrayList<DispositionCard> cards;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        DBConnector db = new DBConnector();
        ArrayList<Disposition> disp = db.listAllOriginalDispositions();
        cards = new ArrayList<>();

        String path = "";
        int disposition_index = 1;

        for(Disposition d: disp)
        {
            DispositionCard current = new DispositionCard(getClass().getResource(d.getImagePath()).getPath(), disposition_index++);
            current.getControl().setOnMouseClicked(e -> onCardMouseClicked(e));
            cards.add(current);
        }

        int x = 0;
        int y = 0;
        for(int i = 0; i < cards.size(); i++)
        {
            grid.add(cards.get(i).getControl(), x++,y);
            GridPane.setMargin(cards.get(i).getControl(), new Insets(16,6,6,6));
            if(x >= 3)
            {
                x = 0;
                y++;
            }
        }
        db.close();
    }

    public void CloseClicked(ActionEvent actionEvent)
    {
        Stage stage = (Stage) grid.getScene().getWindow();
        stage.close();
    }

    public void MenuClicked(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/menu.fxml"));
        OpenWindow(fxmlLoader, "Main Menu", actionEvent);
    }

    private void OpenWindow(FXMLLoader fxmlLoader, String title, Event event)
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

    private void onCardMouseClicked(Event event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/boardgame.fxml"));

        // Open game window
        OpenWindow(fxmlLoader, "Game", event);

        // Get current disposition card
        DispositionCard current = getCurrentDispositionCard(event);

        // Create new game
        GameHandler gameHandler = new GameHandler(current.getDispositionNumber());

        // Communications inter-view
        GameView gameView = fxmlLoader.getController();
        gameView.setController(gameHandler);
    }

    private DispositionCard getCurrentDispositionCard(Event event)
    {
        for (DispositionCard card: cards)
        {
            if(card.getControl() == event.getSource())
                return card;
        }

        throw new IllegalArgumentException();
    }
}