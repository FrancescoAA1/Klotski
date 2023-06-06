package com.klotski.UI;

import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DispositionCardTest {

    private static DispositionCard dispositionCard;

    @BeforeAll
    public static void setup()
    {
        String imagePath = "/com/klotski/images/m1.png";

        int dispositionNumber = 12;

        dispositionCard = new DispositionCard(imagePath, dispositionNumber);
    }

    @Test
    void setImagePath()
    {
        String imagePath = "/com/klotski/images/m2.png";;

        dispositionCard.setImagePath(imagePath);

        assertEquals(imagePath, dispositionCard.getImagePath());
    }

    @Test
    void getImagePath()
    {
        String imagePath = "m2.png";

        String result = dispositionCard.getImagePath();

        assertEquals(imagePath, result);
    }

    @Test
    void setDispositionNumber()
    {
        int dispositionNumber = 2;

        //Setting disposition number
        dispositionCard.setDispositionNumber(dispositionNumber);

        assertEquals(dispositionNumber, dispositionCard.getDispositionNumber());
    }

    @Test
    void getDispositionNumber()
    {

    }
}