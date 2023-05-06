package com.klotski.UI;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class BigSquare extends KlotskiBlock
{
    public BigSquare() { super(2,2); }
    protected Pane GenerateControl()
    {
        Pane rect = super.GenerateControl();
        rect.getStyleClass().add("big_square");
        return rect;
    }
}
