package com.klotski.UI;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import static com.klotski.ViewControllers.GameController.CELLSIDE;
import static com.klotski.ViewControllers.GameController.CELLSPACING;

public class KlotskiBlock
{
    private int Xscale;
    private int Yscale;

    public int getXscale() { return Xscale; }
    public int geYscale() { return Yscale; }
    Pane control;

    public KlotskiBlock(int xScale, int yScale)
    {
        Xscale = xScale;
        Yscale = yScale;
        control = GenerateControl();
    }

    protected Pane GenerateControl()
    {
        Pane rect = new Pane();

        return rect;
    }

    public Pane GetControl()
    {
        return control;
    }
}
