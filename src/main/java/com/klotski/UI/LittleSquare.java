package com.klotski.UI;

import javafx.scene.shape.Rectangle;

import static com.klotski.ViewControllers.GameController.CELLSIDE;
import static com.klotski.ViewControllers.GameController.CELLSPACING;

public class LittleSquare extends KlotskiBlock
{
    public LittleSquare() { super(1,1); }
    protected Rectangle GenerateControl()
    {
        Rectangle rect = super.GenerateControl();
        rect.getStyleClass().add("square");
        return rect;
    }
}
