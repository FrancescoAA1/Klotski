package com.klotski.UI;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class VerticalRectangle extends KlotskiBlock
{
    public VerticalRectangle() { super(1,2); }
    protected Pane GenerateControl()
    {
        Pane rect = super.GenerateControl();
        rect.getStyleClass().add("vertical_rectangle");
        return rect;
    }
}
