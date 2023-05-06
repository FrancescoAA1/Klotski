package com.klotski.UI;

import javafx.scene.shape.Rectangle;

public class BigSquare extends KlotskiBlock
{
    public BigSquare() { super(2,2); }
    protected Rectangle GenerateControl()
    {
        Rectangle rect = super.GenerateControl();
        rect.getStyleClass().add("big_square");
        return rect;
    }
}
