package com.klotski.ViewControllers;

import com.klotski.Controllers.DBConnector;
import com.klotski.UI.DispositionCard;
import com.klotski.UI.SavedGame;
import com.klotski.model.Match;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SavedController implements Initializable {

    @FXML
    private GridPane grid;

    private DBConnector db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        db = new DBConnector();
        ArrayList<Pair<Match, Integer>> matches = db.listAllRecordedMatches();
        System.out.println(matches.size());
        int index = 0;

        for(Pair<Match, Integer> m: matches)
        {
            int score = m.getKey().getScore();
            String date = m.getKey().getName();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            System.out.println(dateTime);
            boolean terminated = m.getKey().isTerminated();

            SavedGame s = new SavedGame(getClass().getResource("/com/klotski/Images/m1.png").getPath(), score, dateTime, terminated);
            grid.add(s.getControl(), 0,index++);
        }

    }

    public void MenuClicked(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/menu.fxml"));
        OpenWindow(fxmlLoader, "Main Menu", actionEvent);
    }

    private void OpenWindow(FXMLLoader fxmlLoader, String title, ActionEvent event)
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