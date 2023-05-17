package com.klotski.View;

import com.klotski.Controllers.DBConnector;
import com.klotski.Controllers.GameHandler;
import com.klotski.UI.DispositionCard;
import com.klotski.UI.SavedGameCard;
import com.klotski.model.Disposition;
import com.klotski.model.Match;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class SavedListView implements Initializable {

    @FXML
    private GridPane grid;
    private GameHandler gameHandler;
    private ArrayList<SavedGameCard> cards;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        DBConnector db = new DBConnector();
        ArrayList<Pair<Match, Integer>> matches = db.listAllRecordedMatches();

        if(matches == null) return;

        cards = new ArrayList<SavedGameCard>();
        int index = 0;

        for(Pair<Match, Integer> m: matches)
        {
            int dispositionID = m.getValue();
            Disposition disp = db.getDisposition(dispositionID);
            if(disp == null) return;

            String imagePath = disp.getImagePath();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            LocalDateTime dateTime = LocalDateTime.parse(m.getKey().getName(), formatter);

            SavedGameCard card = new SavedGameCard(getClass().getResource(imagePath).getPath(), dateTime, m.getKey(), dispositionID);
            card.getControl().setOnMouseClicked(e -> onMouseClicked(e));
            grid.add(card.getControl(), 0,index++);
            cards.add(card);
        }
        db.close();
    }

    private void onMouseClicked(Event event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/boardgame.fxml"));

        // Open game window
        OpenWindow(fxmlLoader, "Game", event);

        // Get current SavedGame card
        SavedGameCard card = getCurrentSavedGameCard(event);

        // Create new game
        GameHandler gameHandler = new GameHandler(card.getDispositionID(), card.getMatch());

        // Communications inter-view
        GameView gameView = fxmlLoader.getController();
        gameView.setController(gameHandler);
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

    private SavedGameCard getCurrentSavedGameCard(Event event)
    {
        for (SavedGameCard card: cards)
        {
            if(card.getControl() == event.getSource())
                return card;
        }

        throw new IllegalArgumentException();
    }
}