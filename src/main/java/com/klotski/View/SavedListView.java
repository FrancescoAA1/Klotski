package com.klotski.View;

import com.klotski.Controllers.DBConnector;
import com.klotski.Controllers.GameHandler;
import com.klotski.UI.DispositionCard;
import com.klotski.UI.SavedGameCard;
import com.klotski.model.Match;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        DBConnector db = new DBConnector();
        ArrayList<Pair<Match, Integer>> matches = db.listAllRecordedMatches();
        System.out.println(matches.size());
        int index = 0;

        for(Pair<Match, Integer> m: matches)
        {
            int score = m.getKey().getScore();
            String date = m.getKey().getName();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            //System.out.println(dateTime);
            LocalDateTime dateTime = LocalDateTime.now();
            boolean terminated = m.getKey().isTerminated();

            SavedGameCard s = new SavedGameCard(getClass().getResource("/com/klotski/Images/m1.png").getPath(), score, dateTime, terminated);
            s.getControl().setOnMouseClicked(e -> onMouseClicked(e));
            grid.add(s.getControl(), 0,index++);
        }
        db.close();
    }

    private void onMouseClicked(Event event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/GUI/boardgame.fxml"));

        // Open game window
        OpenWindow(fxmlLoader, "Game", event);

        // Get current SavedGame card


        // Create new game
        GameHandler gameHandler = new GameHandler(27, 5, false);

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
        stage.show();
    }
}