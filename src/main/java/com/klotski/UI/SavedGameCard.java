package com.klotski.UI;

import com.klotski.Model.Match;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.time.LocalDateTime;

public class SavedGameCard {
    private static final String SEPARATOR = "T";
    private String ImagePath;
    private int dispositionID;
    private int originalDispositionID;
    private Match match;
    private LocalDateTime gameDate;

    public Match getMatch() {
        return match;
    }
    public LocalDateTime getGameDate() {
        return gameDate;
    }
    public int getDispositionID() {
        return dispositionID;
    }
    public int getOriginalDispositionID() {
        return originalDispositionID;
    }


    private Pane control;

    public SavedGameCard(String imagePath, LocalDateTime game_d, Match currentMatch, int disposition_ID, int originalDisposition_ID) {
        ImagePath = imagePath;
        match = currentMatch;
        gameDate = game_d;
        control = GenerateControl();
        dispositionID = disposition_ID;
        originalDispositionID = originalDisposition_ID;
    }

    private Pane GenerateControl()
    {
        // Container
        Pane game = new Pane();
        game.setPrefWidth(280);
        game.setPrefHeight(110);
        game.getStyleClass().add("card");
        game.setOpacity(0.75);

        // Disposition ImageView
        File file = new File(ImagePath);
        ImageView img = new ImageView(new Image(file.toURI().toString()));
        img.setFitHeight(80);
        img.setFitWidth(60);
        img.setLayoutX(15);
        img.setLayoutY(20);
        img.getStyleClass().add("image");

        // DispositionNumber Label
        Label lbl_mov = new Label("MOVE COUNT: " + String.valueOf(match.getScore()));
        lbl_mov.setLayoutX(100);
        lbl_mov.setLayoutY(20);
        lbl_mov.getStyleClass().add("label");

        String[] gameDateParts = gameDate.toString().split(SEPARATOR);

        Label lbl_date = new Label("DATE: " + gameDateParts[0] + " " + gameDateParts[1]);
        lbl_date.setLayoutX(100);
        lbl_date.setLayoutY(40);
        lbl_date.getStyleClass().add("label");

        String state = "";

        if(match.isTerminated()){
            state = "GAME FINISHED";
        }
        else
        {
            state = "IN PROGRESS";
        }
        Label lbl_state = new Label("MATCH STATUS: " + state);
        lbl_state.setLayoutX(100);
        lbl_state.setLayoutY(60);
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
}
