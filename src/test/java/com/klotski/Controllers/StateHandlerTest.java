package com.klotski.Controllers;

import com.klotski.Model.Direction;
import com.klotski.Model.Move;
import com.klotski.Model.Position;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StateHandlerTest {

    private StateHandler stateHandler;
    private static final String FILE_TEST_NAME = "_klotski_test_.hst";
    private static final String SUBFOLDER_PATH = "/target/classes/com/klotski/Data/MatchLogs/";

    @BeforeEach
    void setUp() {
        stateHandler = new StateHandler(FILE_TEST_NAME);
    }
    @AfterEach
    void clean()
    {
        // remove the test file created
        try
        {
            String currentPath = new java.io.File(".").getCanonicalPath();
            File file = new File(currentPath + SUBFOLDER_PATH + FILE_TEST_NAME);
            // some test does not invoke flush so the file could not be created
            if(file.exists())
                assertTrue(file.delete());
        }
        catch(IOException e)
        {fail("Error removing the file used to test: " + e.getMessage());}
    }

    @Test
    void pushMove() {
        Move move = new Move(new Position(1,0), new Position(3,2), Direction.UP);
        stateHandler.pushMove(move);
        assertTrue(stateHandler.hasState());
        assertEquals(move, stateHandler.topMove());
    }

    @Test
    void popMove() {
        // test pop when is empty
        assertFalse(stateHandler.hasState());
        assertNull(stateHandler.popMove());
        // test adding move
        Move move1 = new Move(new Position(1,0), new Position(3,2), Direction.UP);
        Move move2 = new Move(new Position(1,7), new Position(5,2), Direction.LEFT);
        stateHandler.pushMove(move1);
        stateHandler.pushMove(move2);

        Move poppedMove = stateHandler.popMove();
        assertEquals(move2, poppedMove);
        assertTrue(stateHandler.hasState());
        assertEquals(move1, stateHandler.topMove());

        // test try to make the state handler empty
        stateHandler.popMove();
        assertFalse(stateHandler.hasState());
        assertNull(stateHandler.popMove());
    }

    @Test
    void topMove() {
        // test when is empty
        assertNull(stateHandler.topMove());
        // add a move
        Move move = new Move(new Position(1,0), new Position(3,2), Direction.UP);
        stateHandler.pushMove(move);

        assertEquals(move, stateHandler.topMove());
        // removing and trying another time
        stateHandler.popMove();
        assertNull(stateHandler.topMove());
    }

    @Test
    void flush() {
        // trying to flush when state handler is empty -> I'm expecting to obtain only 1 row with count  = 0
        assertTrue(stateHandler.flush());

        // Read the file and check its contents
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();
            BufferedReader reader = new BufferedReader(new FileReader(currentPath + SUBFOLDER_PATH + FILE_TEST_NAME));
            assertEquals("0", reader.readLine()); // Check the round counter
            reader.close();
        } catch (IOException e) {
            fail("Error reading file: " + e.getMessage());
        }

        Move move1 = new Move(new Position(1,0), new Position(3,2), Direction.UP);
        Move move2 = new Move(new Position(1,7), new Position(5,2), Direction.LEFT);
        stateHandler.pushMove(move1);
        stateHandler.pushMove(move2);
        // testing flush with data added
        // doing this I'm also testing that the stateHandler overrides the previous file created
        assertTrue(stateHandler.flush());

        // Read the file and check its contents
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();
            BufferedReader reader = new BufferedReader(new FileReader(currentPath + SUBFOLDER_PATH + FILE_TEST_NAME));
            assertEquals("2", reader.readLine()); // Check the round counter
            assertEquals(move1.toString(), reader.readLine()); // Check the first move
            assertEquals(move2.toString(), reader.readLine()); // Check the second move
            assertNull(reader.readLine());// I've wrote only 2 line so the third must be null
            reader.close();
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    void restoreStatus() {
        // with the AfterEach I've created an empty stateHandler
        stateHandler.flush();
        // trying to restore a status of an empty stateHandler
        stateHandler.restoreStatus();
        assertFalse(stateHandler.hasState());
        // preparing a state
        Move move1 = new Move(new Position(1,0), new Position(3,2), Direction.UP);
        Move move2 = new Move(new Position(1,7), new Position(5,2), Direction.LEFT);
        stateHandler.pushMove(move1);
        stateHandler.pushMove(move2);
        // save status to obtain it
        stateHandler.flush();

        // Clear the current state
        stateHandler.popMove();
        stateHandler.popMove();

        assertTrue(stateHandler.restoreStatus());
        assertTrue(stateHandler.hasState());
        assertEquals(move2, stateHandler.topMove());
        assertEquals(2, stateHandler.getCount());
    }
}