package com.klotski.UI;

import javafx.scene.shape.Rectangle;
import static com.klotski.ViewControllers.GameController.CELLSIDE;
import static com.klotski.ViewControllers.GameController.CELLSPACING;

public class KlotskiBlock
{
    private int Xscale;
    private int Yscale;

    public int getXscale() { return Xscale; }
    public int geYscale() { return Yscale; }
    Rectangle control;

    public KlotskiBlock(int xScale, int yScale)
    {
        Xscale = xScale;
        Yscale = yScale;
        control = GenerateControl();
    }

    protected Rectangle GenerateControl()
    {
        Rectangle rect = new Rectangle();
        rect.setWidth(CELLSIDE*Xscale);
        rect.setHeight(CELLSIDE*Yscale);
        //rect.setWidth(CELLSIDE*Xscale + (Xscale - 1)*CELLSPACING);
        //rect.setHeight(CELLSIDE*Yscale + (Yscale - 1)*CELLSPACING);

        return rect;
    }

    public Rectangle GetControl()
    {
        return control;
    }
}
