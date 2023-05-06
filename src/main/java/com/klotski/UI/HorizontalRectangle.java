package com.klotski.UI;

import javafx.scene.shape.Rectangle;

public class HorizontalRectangle extends KlotskiBlock
{
    public HorizontalRectangle() { super(2,1); }
    protected Rectangle GenerateControl()
    {
        Rectangle rect = super.GenerateControl();
        rect.getStyleClass().add("horizontal_rectangle");
        return rect;
    }
}
