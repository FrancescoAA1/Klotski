package com.klotski.UI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

import java.io.File;

public class DispositionCard {
    private String ImagePath;
    private int DispositionNumber;

    public DispositionCard(String imagePath, int dispositionNumber) {
        ImagePath = imagePath;
        DispositionNumber = dispositionNumber;
    }

    public void SetImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String GetImagePath() {
        return ImagePath;
    }

    public void SetDispositionNumber(int dispositionNumber) {
        DispositionNumber = dispositionNumber;
    }

    public int GetDispositionNumber() {
        return DispositionNumber;
    }

    private Pane GenerateControl() {
        // Container
        Pane card = new Pane();
        card.setPrefWidth(200);
        card.setPrefHeight(200);
        card.getStyleClass().add("disposition_card");

        // Disposition ImageView
        File file = new File(ImagePath);
        ImageView img = new ImageView(new Image(file.toURI().toString()));
        img.setFitHeight(100);
        img.setFitWidth(150);
        img.setLayoutX(15);
        img.setLayoutY(21);
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

        // Add children
        card.getChildren().add(lbl_num);
        card.getChildren().add(img);

        return card;
    }

    public Pane GetControl()
    {
        return GenerateControl();
    }
}
