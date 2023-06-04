package com.klotski.Controllers;

import com.klotski.Model.*;
import com.sun.prism.shader.FillEllipse_LinearGradient_PAD_AlphaTest_Loader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NextMoveGatewayTest {

    private int dispositionNumber;
    private Disposition correctDispositionN;
    private Disposition wrongDispositionN;
    private Disposition globalWrongDisposition1;
    private Disposition globalWrongDisposition2;
    private NextMoveGateway nmg;


    @BeforeEach
    void setUp()
    {
        nmg = new NextMoveGateway();
        // string without the disposition format
        globalWrongDisposition1 = new Disposition("String that does not work for sure!", false);
        // string with the disposition format but missing some character or bad formatted
        globalWrongDisposition2 = new Disposition("1; 0;4 LEFT1-1-0;0#2-2-2;0#1-1-2;2#2-1-0;2#2-1-3;21-20;1#1-1-1;0#1-2-2;4#1-1-0;4#1-2-1;31-1-14#1-1-1;2", false);
    }

    @Test
    void getNextMoveWithDispositionGlobalWrong()
    {
        // test with some un-existing disposition numbers
        dispositionNumber = -1;
        assertThrows(IllegalArgumentException.class, () -> {nmg.GetNextMove(dispositionNumber, globalWrongDisposition1);});
        dispositionNumber = 20;
        assertThrows(IllegalArgumentException.class, () -> {nmg.GetNextMove(dispositionNumber, globalWrongDisposition1);});
        dispositionNumber = 0;
        assertThrows(IllegalArgumentException.class, () -> {nmg.GetNextMove(dispositionNumber, globalWrongDisposition1);});

        // test with a null disposition;
        dispositionNumber = 1;
        assertThrows(IllegalArgumentException.class, () -> {nmg.GetNextMove(dispositionNumber, null);});

        // test with a general illegal disposition and an illegal disposition number
        dispositionNumber = -1;
        assertThrows(IllegalArgumentException.class, () -> {nmg.GetNextMove(dispositionNumber, globalWrongDisposition1);});

        // test with a general illegal disposition -> in this case with a correct disposition number
        // I'm expecting to obtain a null next move
        dispositionNumber = 3;
        assertNull(nmg.GetNextMove(dispositionNumber, globalWrongDisposition1));

        // test with a general bad formatted disposition and an illegal disposition number
        dispositionNumber = -1;
        assertThrows(IllegalArgumentException.class, () -> {nmg.GetNextMove(dispositionNumber, globalWrongDisposition2);});

        // test with a general illegal disposition -> in this case with a correct disposition number
        // I'm expecting to obtain a null next move
        dispositionNumber = 3;
        assertNull(nmg.GetNextMove(dispositionNumber, globalWrongDisposition2));
    }
    @Test
    void getNextMoveWithDisposition1()
    {
        dispositionNumber = 1;
        // test with a correct disposition but not existing for the 1° configuration
        wrongDispositionN = new Disposition("2-1-0;0#2-2-1;0#2-1-3;0#2-1-0;3#1-2-1;3#1-1-0;2#1-2-2;4#1-1-2;2#1-1-1;4#1-1-3;3#1-1-3;2#1-1-1;2", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 1° disposition but bad formatted
        wrongDispositionN.setTextDisposition("2-1-0;02-2-1;0#2-1-3;0#2-1-1;2#1-2-2;2#2-1-3;3#1-1-0#1-1-2;3#1-1-0;3#1-1-2;4#1-1-0;2#1-1-1;4");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(1, 3), new Position(1,4), Direction.DOWN);
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;0#2-1-3;0#2-1-0;2#1-2-1;2#2-1-3;2#1-1-1;3#1-1-2;3#1-1-0;4#1-1-3;4#1-1-1;4#1-1-2;4",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;3#2-1-2;0#2-1-1;0#1-2-2;2#2-1-3;0#1-1-0;2#1-1-1;2#1-1-3;3#1-1-3;4#1-1-2;4#1-1-2;3",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(3, 1), new Position(3,0), Direction.UP);
        correctDispositionN = new Disposition("2-1-0;3#2-2-1;2#2-1-2;0#2-1-0;1#1-2-2;4#2-1-3;1#1-1-0;0#1-1-1;0#1-1-1;4#1-1-1;1#1-1-3;0#1-1-3;3",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
    @Test
    void getNextMoveWithDisposition2()
    {
        dispositionNumber = 2;
        // test with a correct disposition but not existing for the 2° configuration
        wrongDispositionN = new Disposition("2-1-0;3#2-2-1;1#2-1-3;0#2-1-0;1#1-2-2;4#2-1-3;2#1-1-0;0#1-1-1;0#1-1-1;4#1-1-2;0#1-1-2;3#1-1-1;3", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 2° disposition but bad formatted
        wrongDispositionN.setTextDisposition("2-1-0;0#2-2-2;0#2-1-3;2#2-1-0;32#1-2-2;4#1-1-1;2#1-1-1;4#1-1-2;2#1-1-1;0#1-1-1;1");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(1, 3), new Position(1,4), Direction.DOWN);
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;0#2-1-3;0#2-1-0;2#1-2-1;2#1-1-3;2#1-2-1;3#1-1-3;3#1-1-0;4#1-1-3;4#1-1-1;4#1-1-2;4",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;3#2-1-3;0#2-1-1;1#1-2-1;0#1-1-3;3#1-2-2;2#1-1-2;1#1-1-3;4#1-1-0;2#1-1-0;3#1-1-0;4",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(2, 4), new Position(3,4), Direction.RIGHT);
        correctDispositionN = new Disposition("2-1-0;0#2-2-0;2#2-1-3;1#2-1-1;0#1-2-2;0#1-1-0;4#1-2-2;3#1-1-2;1#1-1-2;4#1-1-2;2#1-1-1;4#1-1-3;4",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
    @Test
    void getNextMoveWithDisposition3()
    {
        dispositionNumber = 3;
        // test with a correct disposition but not existing for the 3° configuration
        wrongDispositionN = new Disposition("2-1-0;0#2-2-0;2#2-1-3;1#2-1-1;0#1-2-2;0#1-1-1;4#1-2-2;3#1-1-2;1#1-1-3;4#1-1-2;2#1-1-0;4#1-1-2;4", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 3° disposition but bad formatted
        wrongDispositionN.setTextDisposition("2-1-0;0#2-2-1;0#2-1-#1-1-0;2#1-2-1;2#1-1-3;4#1-1-0;3#1--2;4#1-2-0;4#1-1-3;1#1-1-3;0");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(1, 4), new Position(0,4), Direction.LEFT);
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;0#2-1-3;0#1-1-0;2#1-2-1;2#1-1-3;2#1-1-0;3#1-2-1;3#1-1-3;3#1-2-1;4#1-1-0;4#1-1-3;4",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("2-1-2;0#2-2-1;3#2-1-3;0#1-1-0;0#1-2-0;1#1-1-3;3#1-1-1;0#1-2-0;2#1-1-3;4#1-2-2;2#1-1-0;3#1-1-0;4",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(1, 0), new Position(2,0), Direction.RIGHT);
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;0#2-1-3;2#1-1-0;2#1-2-1;2#1-1-3;4#1-1-0;3#1-2-1;3#1-1-2;4#1-2-0;4#1-1-3;1#1-1-3;0",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
    @Test
    void getNextMoveWithDisposition4()
    {
        dispositionNumber = 4;
        // test with a correct disposition but not existing for the 4° configuration
        wrongDispositionN = new Disposition("2-1-0;0#2-2-0;2#2-1-3;1#2-1-1;0#1-2-2;0#1-1-1;4#1-2-2;3#1-1-2;1#1-1-3;4#1-1-2;2#1-1-0;4#1-1-2;4", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 4° disposition but bad formatted
        wrongDispositionN.setTextDisposition("1-1-3;3#2-2-1;1#1-1-10#2-1-0;0#2-1-2;3#1-2-2;0#1-1-3;4#1-2-0;4#1-1-0;2#1-2-03#1-1-3;1#1-1-3;2");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(1, 4), new Position(0,4), Direction.LEFT);
        correctDispositionN = new Disposition("1-1-0;0#2-2-1;0#1-1-3;0#2-1-0;1#2-1-3;1#1-2-1;2#1-1-0;3#1-2-1;3#1-1-3;3#1-2-1;4#1-1-0;4#1-1-3;4",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("1-1-0;4#2-2-1;3#1-1-2;0#2-1-0;0#2-1-1;0#1-2-2;1#1-1-0;3#1-2-0;2#1-1-3;0#1-2-2;2#1-1-3;3#1-1-3;4",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(1, 1), new Position(2,1), Direction.RIGHT);
        correctDispositionN = new Disposition("1-1-3;3#2-2-1;1#1-1-1;0#2-1-0;0#2-1-2;3#1-2-2;0#1-1-3;4#1-2-0;4#1-1-0;2#1-2-0;3#1-1-3;1#1-1-3;2",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
    @Test
    void getNextMoveWithDisposition5()
    {
        dispositionNumber = 5;
        // test with a correct disposition but not existing for the 5° configuration
        wrongDispositionN = new Disposition("2-1-0;0#2-2-0;2#2-1-3;1#2-1-1;0#1-2-2;0#1-1-1;4#1-2-2;3#1-1-2;1#1-1-3;4#1-1-2;2#1-1-0;4#1-1-2;4", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 5° disposition but bad formatted
        wrongDispositionN.setTextDisposition("2-1-1;1#2-2-2;3#1-1-0;2#");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(0, 4), new Position(1,4), Direction.RIGHT);
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;0#1-1-3;0#1-1-3;1#1-2-0;2#1-2-2;2#1-2-0;3#1-2-2;3#1-1-0;4#1-1-3;4#1-1-2;4#1-1-1;4",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("2-1-1;0#2-2-1;3#1-1-0;3#1-1-0;1#1-2-2;0#1-2-2;1#1-2-0;2#1-2-2;2#1-1-0;4#1-1-0;0#1-1-3;3#1-1-3;4",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(2, 0), new Position(1,0), Direction.LEFT);
        correctDispositionN = new Disposition("2-1-1;1#2-2-2;3#1-1-0;2#1-1-2;0#1-2-2;1#1-2-2;2#1-2-0;4#1-2-0;3#1-1-0;1#1-1-3;0#1-1-0;0#1-1-1;0",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
    @Test
    void getNextMoveWithDisposition6()
    {
        dispositionNumber = 6;
        // test with a correct disposition but not existing for the 6° configuration
        wrongDispositionN = new Disposition("2-1-0;0#2-2-0;2#2-1-3;1#2-1-1;0#1-2-2;0#1-1-1;4#1-2-2;3#1-1-2;1#1-1-3;4#1-1-2;2#1-1-0;4#1-1-2;4", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 6° disposition but bad formatted
        wrongDispositionN.setTextDisposition("2-2");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(1, 0), new Position(0,0), Direction.LEFT);
        correctDispositionN = new Disposition("2-2-1;0#2-1-3;0#1-1-0;2#1-1-1;2#1-1-2;2#1-1-3;2#1-2-0;3#1-2-2;3#1-2-0;4#1-2-2;4#1-1-0;0#1-1-0;1",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("2-2-1;3#2-1-2;0#1-1-3;4#1-1-3;1#1-1-3;0#1-1-3;3#1-2-0;0#1-2-0;1#1-2-2;2#1-2-0;2#1-1-0;3#1-1-0;4",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(1, 3), new Position(0,3), Direction.LEFT);
        correctDispositionN = new Disposition("2-2-0;1#2-1-2;1#1-1-3;3#1-1-3;2#1-1-3;1#1-1-1;3#1-2-2;0#1-2-0;0#1-2-0;4#1-2-2;4#1-1-0;3#1-1-2;3",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
    @Test
    void getNextMoveWithDisposition7()
    {
        dispositionNumber = 7;
        // test with a correct disposition but not existing for the 7° configuration
        wrongDispositionN = new Disposition("2-1-0;0#2-2-0;2#2-1-3;1#2-1-1;0#1-2-2;0#1-1-1;4#1-2-2;3#1-1-2;1#1-1-3;4#1-1-2;2#1-1-0;4#1-1-2;4", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 7° disposition but bad formatted
        wrongDispositionN.setTextDisposition("2-1-0;1#2-2-2;2#1-1-2;0#1-1-11-2-0;0#1-1-3;4#1-1-2;4#2-1-1;2#1-2-0;4#2-1-3;0#1-1-2;1#1-1-0;3");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(1, 3), new Position(1,4), Direction.DOWN);
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;0#1-1-3;0#1-1-3;1#1-2-0;2#1-1-2;2#1-1-3;2#2-1-0;3#1-2-1;3#2-1-3;3#1-1-1;4#1-1-2;4",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;3#1-1-2;1#1-1-2;2#1-2-2;0#1-1-0;3#1-1-0;4#2-1-1;0#1-2-0;2#2-1-3;1#1-1-3;3#1-1-3;4",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(1, 1), new Position(2,1), Direction.RIGHT);
        correctDispositionN = new Disposition("2-1-0;1#2-2-2;2#1-1-2;0#1-1-1;1#1-2-0;0#1-1-3;4#1-1-2;4#2-1-1;2#1-2-0;4#2-1-3;0#1-1-2;1#1-1-0;3",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
    @Test
    void getNextMoveWithDisposition8()
    {
        dispositionNumber = 8;
        // test with a correct disposition but not existing for the 8° configuration
        wrongDispositionN = new Disposition("2-1-0;0#2-2-0;2#2-1-3;1#2-1-1;0#1-2-2;0#1-1-1;4#1-2-2;3#1-1-2;1#1-1-3;4#1-1-2;2#1-1-0;4#1-1-2;4", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 8° disposition but bad formatted
        wrongDispositionN.setTextDisposition("2-1-0;3#2-2-0;1#2-1-3;0#1-1-2;2#1-1-2;3#1-1-0;0#1-1-2;0#2-1-1;3#1-2-2;4#2-1-3;2#1-1-1;");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(1, 3), new Position(1,4), Direction.DOWN);
        correctDispositionN = new Disposition("2-1-0;0#2-2-1;0#2-1-3;0#1-1-0;2#1-1-1;2#1-1-2;2#1-1-3;2#2-1-0;3#1-2-1;3#2-1-3;3#1-1-1;4#1-1-2;4",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("2-1-1;0#2-2-1;3#2-1-2;0#1-1-3;4#1-1-3;3#1-1-0;2#1-1-1;2#2-1-0;0#1-2-2;2#2-1-3;0#1-1-0;3#1-1-0;4",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(2, 0), new Position(1,0), Direction.LEFT);
        correctDispositionN = new Disposition("2-1-0;3#2-2-0;1#2-1-3;0#1-1-2;2#1-1-2;3#1-1-0;0#1-1-2;0#2-1-1;3#1-2-2;4#2-1-3;2#1-1-1;0#1-1-2;1",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
    @Test
    void getNextMoveWithDisposition9()
    {
        dispositionNumber = 9;
        // test with a correct disposition but not existing for the 9° configuration
        wrongDispositionN = new Disposition("2-1-0;0#2-2-0;2#2-1-3;1#2-1-1;0#1-2-2;0#1-1-1;4#1-2-2;3#1-1-2;1#1-1-3;4#1-1-2;2#1-1-0;4#1-1-2;4", false);
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        // test with an existing configuration for the 9° disposition but bad formatted
        wrongDispositionN.setTextDisposition("1-1-0;0#2-2-2;;3#2-1-0;3#2-1-3;2#2-1-2;-1;0#1-11-2-2;4#1-1-1;1#1-1-01");
        assertNull(nmg.GetNextMove(dispositionNumber, wrongDispositionN));
        //test with the starting disposition -> expected the first move
        Move firstMove = new Move(new Position(3, 3), new Position(2,3), Direction.LEFT);
        correctDispositionN = new Disposition("1-1-0;0#2-2-1;0#1-1-3;0#2-1-0;1#2-1-3;1#2-1-1;2#1-1-0;3#1-1-3;3#1-2-0;4#1-2-2;4#1-1-2;2#1-1-2;3",false);
        assertEquals(firstMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with the last disposition -> I'm expecting a null disposition because the last one is the winning disposition
        // and there are not more move to do
        correctDispositionN = new Disposition("1-1-1;1#2-2-1;3#1-1-3;3#2-1-0;1#2-1-3;0#2-1-2;0#1-1-1;2#1-1-3;4#1-2-0;0#1-2-2;2#1-1-0;3#1-1-0;4",false);
        assertNull(nmg.GetNextMove(dispositionNumber, correctDispositionN));
        // test with a general disposition
        Move casualMove = new Move(new Position(0, 2), new Position(0,1), Direction.UP);
        correctDispositionN = new Disposition("1-1-0;0#2-2-2;0#1-1-1;3#2-1-0;3#2-1-3;2#2-1-2;2#1-1-1;0#1-1-1;4#1-2-0;2#1-2-2;4#1-1-1;1#1-1-0;1",false);
        assertEquals(casualMove, nmg.GetNextMove(dispositionNumber, correctDispositionN));
    }
}