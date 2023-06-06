package com.klotski.Controllers;

import com.klotski.Model.*;
import com.klotski.View.GameView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameHandlerTest
{
    DBConnector dbConnector;
    Match validMatch;
    Match invalidMatch;
    Disposition validDisposition;
    int savedDispositionID;

    @BeforeEach
    void setUp() {

        //Opening a new DBConnection
        dbConnector = new DBConnector();
        dbConnector.connect();

        //Creating Valid and Invalid Match
        validMatch = new Match("2023-05-27_20-59-08");
        invalidMatch = null;

        //Creating Valid and Invalid Disposition
        validDisposition = new Disposition("2-1-0;0#2-2-1;0#2-1-3;0#2-1-0;2#1-2-1;2#2-1-3;2#1-1-1;3#1-1-2;3#1-1-0;4#1-1-3;4#1-1-1;4#1-1-2;4", true);
        validDisposition.setImagePath("/com/klotski/Images/m1.png");
        validDisposition.setOriginalNumber(1);

        // Save valid disposition
        dbConnector.saveMatch(validMatch, validDisposition);
        savedDispositionID = dbConnector.lastSavedDispositionID();

        dbConnector.close();
    }

    @Test
    void constructorTest()
    {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());
        assertNotNull(controller);

        // Create controller for invalid new game (not existent)
        assertThrows(IllegalArgumentException.class, () -> {new GameHandler(1000);});
        // Create controller for invalid new game (not original)
        assertThrows(IllegalArgumentException.class, () -> {new GameHandler(savedDispositionID);});

        // Create controller for valid load game
        controller = new GameHandler(savedDispositionID,validMatch);
        assertNotNull(controller);

        // Create controller for invalid load game (match null)
        assertThrows(IllegalArgumentException.class, () -> {new GameHandler(savedDispositionID, invalidMatch);});

        // Create controller for invalid load game (saved disposition not existent)
        assertThrows(IllegalArgumentException.class, () -> {new GameHandler(1000,validMatch);});
        // Create controller for invalid load game (saved disposition original)
        assertThrows(IllegalArgumentException.class, () -> {new GameHandler(validDisposition.getOriginalNumber(),validMatch);});
    }

    @Test
    void checkMove()
    {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());

        // Valid Move
        assertTrue(controller.checkMove(new Position(0,4), Direction.RIGHT));
        controller.move(new Position(0,4), Direction.RIGHT);
        assertTrue(controller.checkMove(new Position(0,2), Direction.DOWN));
        controller.move(new Position(0,2), Direction.DOWN);

        // Invalid Move (Block not existent)
        assertFalse(controller.checkMove(new Position(0,4), Direction.RIGHT));
        assertFalse(controller.checkMove(new Position(0,2), Direction.DOWN));
        // Invalid Move (not valid)
        assertFalse(controller.checkMove(new Position(1,3), Direction.DOWN));
        assertFalse(controller.checkMove(new Position(1,4), Direction.LEFT));
    }

    @Test
    void move()
    {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());

        // Valid Move
        assertTrue(controller.move(new Position(0,4), Direction.RIGHT));
        assertTrue(controller.move(new Position(0,2), Direction.DOWN));

        // Invalid Move (Block not existent)
        assertFalse(controller.move(new Position(0,4), Direction.RIGHT));
        assertFalse(controller.move(new Position(0,2), Direction.DOWN));
        // Invalid Move (not valid)
        assertFalse(controller.move(new Position(1,3), Direction.DOWN));
        assertFalse(controller.move(new Position(1,4), Direction.LEFT));
    }

    @Test
    void undo()
    {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());

        // Valid Move
        controller.move(new Position(0,4), Direction.RIGHT);
        controller.move(new Position(0,2), Direction.DOWN);

        // Undo
        assertTrue(controller.undo());
        assertTrue(controller.undo());
        assertFalse(controller.undo());
    }

    @Test
    void hint() {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());

        // Valid Move
        controller.move(new Position(0,4), Direction.RIGHT);
        controller.move(new Position(0,2), Direction.DOWN);

        // Valid Hint
        Random rnd = new Random();
        int bound = rnd.nextInt(0,20);
        for(int i = 0; i < bound; i++)
        {
            assertTrue(controller.hint());
        }
    }

    @Test
    void reset() {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());

        // Valid Move
        controller.move(new Position(0,4), Direction.RIGHT);
        controller.move(new Position(0,2), Direction.DOWN);

        // Valid Hint
        Random rnd = new Random();
        int bound = rnd.nextInt(0,20);
        for(int i = 0; i < bound; i++)
        {
            controller.hint();
        }

        // Reset all
        controller.reset();
        assertTrue(controller.getMoveCounter() == 0);
        assertTrue(controller.getHintCounter() == 0);
    }

    @Test
    void isSolved() {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());
        assertFalse(controller.isSolved());

        for(int i = 0; i < 118; i++)
            controller.hint();

        assertTrue(controller.isSolved());
    }

    @Test
    void getAllBlocks() {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());
        ArrayList<Block> blocks = controller.getAllBlocks();
        ArrayList<Block> expected = new ArrayList<Block>();
        expected.add(new Block(new Position(0,0), 1, 2));
        expected.add(new Block(new Position(1,0), 2, 2));
        expected.add(new Block(new Position(3,0), 1, 2));
        expected.add(new Block(new Position(0,2), 1, 2));
        expected.add(new Block(new Position(1,2), 2, 1));
        expected.add(new Block(new Position(3,2), 1, 2));
        expected.add(new Block(new Position(1,3), 1, 1));
        expected.add(new Block(new Position(2,3), 1, 1));
        expected.add(new Block(new Position(0,4), 1, 1));
        expected.add(new Block(new Position(3,4), 1, 1));

        for(int i = 0; i < blocks.size(); i++)
        {
            boolean found = false;

            for(int j = 0; j < expected.size() && !found; j++)
            {
                if(blocks.get(i).getPos().equals(expected.get(j).getPos())
                    && blocks.get(i).getHeight() == expected.get(j).getHeight()
                    && blocks.get(i).getWidth() == expected.get(j).getWidth()
                    && blocks.get(i).isSpecial() == expected.get(j).isSpecial())
                {
                    found = true;
                    expected.remove(j);
                }

            }

            assertTrue(found);
        }
    }

    @Test
    void getMoveCounter() {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());
        assertTrue(controller.getMoveCounter() == 0);

        // Valid Move
        controller.move(new Position(0,4), Direction.RIGHT);
        controller.move(new Position(0,2), Direction.DOWN);

        // Check counter
        assertTrue(controller.getMoveCounter() == 2);
    }

    @Test
    void getHintCounter() {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());
        assertTrue(controller.getHintCounter() == 0);

        // Valid Hint
        Random rnd = new Random();
        int bound = rnd.nextInt(0,20);
        for(int i = 0; i < bound; i++)
        {
            controller.hint();
        }

        // Check counter
        assertTrue(controller.getHintCounter() == bound);
    }

    @Test
    void getPositionOfLastMovedBlock() {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());
        Position expected = new Position(1, 4);

        // Valid Move
        controller.move(new Position(0,4), Direction.RIGHT);
        assertTrue(expected.equals(controller.getPositionOfLastMovedBlock()));

        expected = new Position(0, 3);
        controller.move(new Position(0,2), Direction.DOWN);
        assertTrue(expected.equals(controller.getPositionOfLastMovedBlock()));
    }

    @Test
    void saveGame()
    {
        // Create controller for valid new game
        GameHandler controller = new GameHandler(validDisposition.getOriginalNumber());
        assertTrue(controller.saveGame());

        // Create controller for valid load game
        controller = new GameHandler(savedDispositionID,validMatch);
        assertTrue(controller.saveGame());
    }
}