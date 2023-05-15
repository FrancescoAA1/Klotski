package com.klotski.UI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;

import java.io.File;
import java.time.LocalDateTime;

public class SavedGameCard {
    private String ImagePath;
    private int moveNumber;
    private int dispositionID;

    private LocalDateTime gameDate;

    private boolean gameState;

    public int getMoveNumber() {
        return moveNumber;
    }
    public LocalDateTime getGameDate() {
        return gameDate;
    }
    public int getDispositionID() {
        return dispositionID;
    }
    public boolean getGameState() {
        return gameState;
    }


    private Pane control;

    public SavedGameCard(String imagePath, int move_n, LocalDateTime game_d, boolean game_s, int disposition_ID) {
        ImagePath = imagePath;
        moveNumber = move_n;
        gameDate = game_d;
        gameState = game_s;
        control = GenerateControl();
        dispositionID = disposition_ID;
    }

    private Pane GenerateControl()
    {
        // Container
        Pane game = new Pane();
        game.setPrefWidth(200);
        game.setPrefHeight(200);

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

}
