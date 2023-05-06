package com.klotski.UI;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class LittleSquare extends KlotskiBlock
{
    public LittleSquare() { super(1,1); }
    protected Pane GenerateControl()
    {
        Pane rect = super.GenerateControl();
        rect.getStyleClass().add("square");
        return rect;
    }
}
