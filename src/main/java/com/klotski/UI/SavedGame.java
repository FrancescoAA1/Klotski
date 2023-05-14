package com.klotski.UI;

import com.klotski.ViewControllers.GameController;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.text.SimpleDateFormat;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class SavedGame {
    private String ImagePath;
    private int moveNumber;

    private LocalDateTime gameDate;

    private boolean gameState;


    private Pane control;

    public SavedGame(String imagePath, int move_n, LocalDateTime game_d, boolean game_s) {
        ImagePath = imagePath;
        moveNumber = move_n;
        gameDate = game_d;
        gameState = game_s;
        control = GenerateControl();
    }

    public void SetImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String GetImagePath() {
        return ImagePath;
    }

    public void SetMoveNumber(int m) {
        moveNumber = m;
    }
    public int GetMoveNumber() { return moveNumber; }

    public void SetGameDate(LocalDateTime game) {
        gameDate = game;
    }
    public LocalDateTime GetGameDate() { return gameDate; }

    public void SetGameState(boolean state) {
        gameState = state;
    }
    public boolean GetGameState() { return gameState; }


    private Pane GenerateControl()
    {
        // Container
        Pane game = new Pane();
        game.setPrefWidth(200);
        game.setPrefHeight(200);
        game.setOnMouseClicked(e -> onMouseClicked(e));

        game.setOpacity(0.75);

        // Disposition ImageView
        File file = new File(ImagePath);
        ImageView img = new ImageView(new Image(file.toURI().toString()));
        img.setFitHeight(80);
        img.setFitWidth(60);
        img.setLayoutX(25);
        img.setLayoutY(5);

        // DispositionNumber Label
        Label lbl_mov = new Label("NUMERO DI MOSSE: " + String.valueOf(moveNumber));
        lbl_mov.setLayoutX(109);
        lbl_mov.setLayoutY(14);
        lbl_mov.getStyleClass().add("label");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Label lbl_date = new Label("DATA: " + gameDate);
        lbl_date.setLayoutX(109);
        lbl_date.setLayoutY(37);
        lbl_date.getStyleClass().add("label");

        String state = "";

        if(gameState){
            state = "TERMINATA";
        }
        else
        {
            state = "NON TERMINATA";
        }
        Label lbl_state = new Label("STATO PARTITA: " + state);
        lbl_state.setLayoutX(109);
        lbl_state.setLayoutY(59);
        lbl_state.getStyleClass().add("label");


        // Add children
        game.getChildren().add(lbl_state);
        game.getChildren().add(lbl_mov);
        game.getChildren().add(lbl_date);
        game.getChildren().add(img);

        return game;
    }

    public Pane getControl()
    {
        return control;
    }

    private void onMouseClicked(Event event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klotski/View/boardgame.fxml"));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Klotski");
        stage.setScene(scene);
        stage.show();
    }
}
