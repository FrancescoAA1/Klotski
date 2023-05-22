package com.klotski.UI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

import java.io.File;

public class DispositionCard {
    private String ImagePath;
    private int DispositionNumber;

    private ScaleTransition scaleIn;
    private ScaleTransition scaleOut;
    private FadeTransition fadeIn;
    private FadeTransition fadeOut;

    private Pane control;

    public DispositionCard(String imagePath, int dispositionNumber) {
        ImagePath = imagePath;
        DispositionNumber = dispositionNumber;
        control = generateControl();
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setDispositionNumber(int dispositionNumber) {
        DispositionNumber = dispositionNumber;
    }

    public int getDispositionNumber() { return DispositionNumber; }

    private Pane generateControl()
    {
        // Container
        Pane card = new Pane();
        card.setPrefWidth(200);
        card.setPrefHeight(200);
        card.getStyleClass().add("card");
        fadeIn = new FadeTransition(Duration.millis(100));
        fadeIn.setNode(card);
        fadeIn.setToValue(1);
        card.setOnMouseEntered(e -> onMouseEntered());

        fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(card);
        fadeOut.setToValue(0.85);
        card.setOnMouseExited(e -> onMouseExited());

        card.setOpacity(0.85);

        // Disposition ImageView
        File file = new File(ImagePath);
        ImageView img = new ImageView(new Image(file.toURI().toString()));
        img.setFitHeight(100);
        img.setFitWidth(150);
        img.setLayoutX(13);
        img.setLayoutY(19);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);

        // DispositionNumber Label
        Label lbl_num = new Label(String.valueOf(DispositionNumber));
        lbl_num.setLayoutX(42);
        lbl_num.setLayoutY(-12);
        lbl_num.setPrefWidth(24);
        lbl_num.setPrefHeight(24);
        lbl_num.setTextAlignment(TextAlignment.CENTER);
        lbl_num.getStyleClass().add("circle_label");

        scaleIn = new ScaleTransition(Duration.millis(100), lbl_num);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);
        scaleOut = new ScaleTransition(Duration.millis(100), lbl_num);
        scaleOut.setToX(1);
        scaleOut.setToY(1);

        // Add children
        card.getChildren().add(lbl_num);
        card.getChildren().add(img);

        return card;
    }

    public Pane getControl()
    {
        return control;
    }

    private void onMouseEntered()
    {
        fadeIn.playFromStart();
        scaleIn.playFromStart();;
    }
    private void onMouseExited()
    {
        fadeOut.playFromStart();
        scaleOut.playFromStart();
    }
}
